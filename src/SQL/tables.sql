create table voivodeships
(
    id   integer primary key,
    name text not null
);

create table rivers
(
    id   integer primary key,
    name text not null
);

create table stations
(
    id             integer primary key,
    name           text    not null,
    river_id       integer not null references rivers (id),
    voivodeship_id integer not null references voivodeships (id),
    latitude       real,   --nullable for inserting new stations, coordinates might be added later
    longitude      real
);

----główne pomiary poziomu wody i przepływu---
create table water_levels
(
    id         integer primary key,
    station_id integer not null references stations (id),
    level      real not null,
    time       timestamp,
    flow_rate  real,
    flow_date  timestamp
);

alter table water_levels
    add constraint unique_station_time unique (station_id, time);

create index idx_water_levels_time on water_levels (station_id, time desc);

------dodatkowe pomiary, nie do końca aktualne-----
create table other_measurements
(
    id         integer primary key,
    station_id integer not null references stations (id),
    water_temp real,
    water_temp_date timestamp,
    ice_phenomena integer,
    ice_phenomena_date timestamp,
    overgrowth integer,
    overgrowth_date timestamp
);

alter table other_measurements
    add constraint unique_station_all_dates
        unique (
                station_id,
                water_temp_date,
                ice_phenomena_date,
                overgrowth_date
            );

---error logi, internal-----
create table error_logs
(
    function_name text not null,
    error_message text not null,
    error_time    timestamp default now() not null
);

----views-----

--widok do podsumowania stacji z aktualnymi pomiarami wody i innymi pomiarami---
create or replace view station_latest_measurements as
select
    s.id as station_id,
    s.name as station_name,
    s.latitude,
    s.longitude,
    r.name as river_name,
    v.name as voivodeship_name,
    wl.level,
    wl.time as level_time,
    wl.flow_rate,
    wl.flow_date,
    om.water_temp,
    om.water_temp_date,
    om.ice_phenomena,
    om.ice_phenomena_date,
    om.overgrowth,
    om.overgrowth_date
from stations s
         join rivers r on s.river_id = r.id
         join voivodeships v on s.voivodeship_id = v.id
         left join lateral (
    select *
    from water_levels wl
    where wl.station_id = s.id
    order by wl.time desc
    limit 1
    ) wl on true
         left join lateral (
    select *
    from other_measurements om
    where om.station_id = s.id
    order by water_temp_date desc nulls last
    limit 1
    ) om on true;


----widok do podsumowania stacji z aktualnym poziomem wody i przepływem---
create or replace view station_current_levels as
select
    s.id as station_id,
    s.name as station_name,
    s.latitude,
    s.longitude,
    wl.level as current_level,
    wl.flow_rate as current_flow_rate
from stations s
         left join lateral (
    select level, time, flow_rate, flow_date
    from water_levels wl
    where wl.station_id = s.id
    order by time desc
    limit 1
    ) wl on true
where s.latitude is not null and s.longitude is not null;

----widok do podsumowania stacji z województwami---
create or replace view stations_with_voivodeship as
select
    s.id as station_id,
    s.name as station_name,
    s.latitude,
    s.longitude,
    v.name as voivodeship_name
from stations s
         join voivodeships v on s.voivodeship_id = v.id;

---widok do podsumowania stacji z rzekami---
create or replace view stations_with_river as
select
    s.id as station_id,
    s.name as station_name,
    s.latitude,
    s.longitude,
    r.name as river_name
from stations s
         join rivers r on s.river_id = r.id;
