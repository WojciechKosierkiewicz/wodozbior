create sequence rivers_id_seq;

create sequence voivodeships_id_seq;

create sequence measurements_id_seq;

create type proc_result_type as
(
    result  boolean,
    message text
);

create type error_log_type as
(
    function_name text,
    error_message text,
    error_time    timestamp
);

create or replace function delete_old_logs() returns trigger
    language plpgsql
as
$$
begin
    delete from error_logs where error_time < now() - interval '1 month';
    return null;
end;
$$;

create or replace procedure addriver(IN in_rivername text, OUT out_result proc_result_type)
    language plpgsql
as
$$
    declare
        v_riverId integer;
        error_message error_log_type;
begin
    error_message := ('addRiver', '',now());

    select id into v_riverId from rivers where name = in_riverName;

    begin
        out_result := true;
        insert into rivers (id, name)
        values (nextval('rivers_id_seq'), in_riverName);
    exception
        when unique_violation then
            out_result.result := false;
            out_result.message := format('River % already exists', in_riverName);
            error_message.error_message := out_result.message;
            insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        when foreign_key_violation then
            out_result.result := false;
            out_result.message := format('Foreign key violation for river %', in_riverName);
            error_message.error_message := out_result.message;
            insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        when others then
            RAISE EXCEPTION 'Unexpected error: %', SQLERRM;
    end;

    out_result.result := true;
    out_result.message := format('River % added successfully', in_riverName);
    return;
end;
$$;

create or replace procedure addvoivodeship(IN in_voivodeshipname text, OUT out_result proc_result_type)
    language plpgsql
as
$$
declare
    error_message error_log_type;
begin
    error_message := ('addVoivodeship', '', now());

    if exists (select 1 from voivodeships where name = in_voivodeshipName) then
        out_result.result := false;
        out_result.message := format('Voivodeship %s already exists', in_voivodeshipName);
        error_message.error_message := out_result.message;
        insert into error_logs (function_name, error_message, error_time)
        values (error_message.function_name, error_message.error_message, error_message.error_time);
        return;
    end if;

    begin
        insert into voivodeships (id, name)
        values (nextval('voivodeships_id_seq'), in_voivodeshipName);
    exception
        when foreign_key_violation then
            out_result.result := false;
            out_result.message := format('Foreign key violation for voivodeship %s', in_voivodeshipName);
            error_message.error_message := out_result.message;
            insert into error_logs (function_name, error_message, error_time)
            values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        when others then
            RAISE EXCEPTION 'Unexpected error in addVoivodeship(): %', SQLERRM;
    end;

    out_result.result := true;
end;
$$;

create or replace procedure addstation(IN in_stationid integer, IN in_stationname text, IN in_rivername text, IN in_voivodeshipname text, OUT out_result proc_result_type)
    language plpgsql
as
$$
declare
    v_riverId integer;
    v_voivodeshipId integer;
    error_message error_log_type;
begin
    error_message := ('addStation', '', now());

    if not exists (select 1 from rivers where name = in_riverName) then
        out_result.result := false;
        out_result.message := format('River %s does not exist', in_riverName);
        error_message.error_message := out_result.message;
        insert into error_logs (function_name, error_message, error_time)
        values (error_message.function_name, error_message.error_message, error_message.error_time);
        return;
    end if;

    select id into v_riverId from rivers where name = in_riverName;

    if not exists (select 1 from voivodeships where name = in_voivodeshipName) then
        out_result.result := false;
        out_result.message := format('Voivodeship %s does not exist', in_voivodeshipName);
        error_message.error_message := out_result.message;
        insert into error_logs (function_name, error_message, error_time)
        values (error_message.function_name, error_message.error_message, error_message.error_time);
        return;
    end if;

    select id into v_voivodeshipId from voivodeships where name = in_voivodeshipName;

    begin
        insert into stations (id, name, river_id, voivodeship_id)
        values (in_stationID, in_stationName, v_riverId, v_voivodeshipId);
    exception
        when unique_violation then
            out_result.result := false;
            out_result.message := format('Station %s already exists', in_stationName);
            error_message.error_message := out_result.message;
            insert into error_logs (function_name, error_message, error_time)
            values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        when foreign_key_violation then
            out_result.result := false;
            out_result.message := format('Foreign key violation for station %s', in_stationName);
            error_message.error_message := out_result.message;
            insert into error_logs (function_name, error_message, error_time)
            values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        when others then
            RAISE EXCEPTION 'Unexpected error in addStation(): %', SQLERRM;
    end;

    out_result.result := true;
end;
$$;

create or replace procedure alterstation(IN in_stationid integer, IN in_longitude real, IN in_latitude real, OUT out_result proc_result_type)
    language plpgsql
as
$$
declare
    error_message error_log_type;
begin
    error_message := ('alterStation', '', now());

    if not exists (select 1 from stations where id = in_stationID) then
        out_result.result := false;
        out_result.message := format('Station with ID %s does not exist', in_stationID);
        error_message.error_message := out_result.message;
        insert into error_logs (function_name, error_message, error_time)
        values (error_message.function_name, error_message.error_message, error_message.error_time);
        return;
    end if;

    begin
        update stations
        set latitude = in_latitude,
            longitude = in_longitude
        where id = in_stationID;
    exception
        when foreign_key_violation then
            out_result.result := false;
            out_result.message := format('Foreign key violation while updating station ID %s', in_stationID);
            error_message.error_message := out_result.message;
            insert into error_logs (function_name, error_message, error_time)
            values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        when others then
            RAISE EXCEPTION 'Unexpected error in alterStation(): %', SQLERRM;
    end;

    out_result.result := true;
end;
$$;

create or replace function insert_water_level(in_station_id integer, in_level real, in_time timestamp without time zone, in_flow_rate real, in_flow_date timestamp without time zone) returns proc_result_type
    language plpgsql
as
$$
declare
    out_result proc_result_type;
begin
    insert into water_levels (station_id, level, time, flow_rate, flow_date)
    values (in_station_id, in_level, in_time, in_flow_rate, in_flow_date)
    on conflict (station_id, time) do nothing;

    -- Check if insert succeeded
    if found then
        out_result.result := true;
        out_result.message := 'New water level added.';
    else
        out_result.result := false;
        out_result.message := 'Measurement already exists.';
    end if;

    return out_result;
end;
$$;

create or replace procedure addmeasurement(IN in_stationid integer, IN in_stationname text, IN in_rivername text, IN in_voivodeshipname text, IN in_level real, IN in_time timestamp without time zone, IN in_flow_rate real, IN in_flow_date timestamp without time zone)
    language plpgsql
as
$$
declare
    river_result proc_result_type;
    voivodeship_result proc_result_type;
    station_result proc_result_type;
    insert_result proc_result_type;
    error_message error_log_type;
begin
    error_message.function_name := 'addMeasurement';
    error_message.error_time := now();

    -- Ensure river exists
    begin
        call addRiver(in_riverName, river_result);
    exception when others then
        error_message.error_message := format('Failed to add river: %s', SQLERRM);
        insert into error_logs (function_name, error_message, error_time)
        values (error_message.function_name, error_message.error_message, error_message.error_time);
        return;
    end;

    -- Ensure voivodeship exists
    begin
        call addVoivodeship(in_voivodeshipName, voivodeship_result);
    exception when others then
        error_message.error_message := format('Failed to add voivodeship: %s', SQLERRM);
        insert into error_logs (function_name, error_message, error_time)
        values (error_message.function_name, error_message.error_message, error_message.error_time);
        return;
    end;

    -- Step 3: Ensure station exists
    begin
        call addStation(in_stationID, in_stationName, in_riverName, in_voivodeshipName, station_result);
    exception when others then
        error_message.error_message := format('Failed to add station: %s', SQLERRM);
        insert into error_logs (function_name, error_message, error_time)
        values (error_message.function_name, error_message.error_message, error_message.error_time);
        return;
    end;

    -- Step 4: Insert measurement (only if new)
    begin
        select * from insert_water_level(
                in_stationID,
                in_level,
                in_time,
                in_flow_rate,
                in_flow_date
                      ) into insert_result;

        if insert_result.result = false then
            -- Optional: log duplicate or failure
            error_message.error_message := format('Insert skipped or failed: %s', insert_result.message);
            insert into error_logs (function_name, error_message, error_time)
            values (error_message.function_name, error_message.error_message, error_message.error_time);
        end if;

    exception when others then
        error_message.error_message := format('Failed to insert measurement: %s', SQLERRM);
        insert into error_logs (function_name, error_message, error_time)
        values (error_message.function_name, error_message.error_message, error_message.error_time);
    end;
end;
$$;

create or replace procedure addmeasurement(IN in_stationid integer, IN in_stationname text, IN in_rivername text DEFAULT NULL::text, IN in_voivodeshipname text DEFAULT NULL::text, IN in_latitude real DEFAULT NULL::real, IN in_longitude real DEFAULT NULL::real, IN in_level real DEFAULT NULL::real, IN in_level_time timestamp without time zone DEFAULT NULL::timestamp without time zone, IN in_flow_rate real DEFAULT NULL::real, IN in_flow_date timestamp without time zone DEFAULT NULL::timestamp without time zone, IN in_water_temp real DEFAULT NULL::real, IN in_water_temp_date timestamp without time zone DEFAULT NULL::timestamp without time zone, IN in_ice_phenomena integer DEFAULT NULL::integer, IN in_ice_phenomena_date timestamp without time zone DEFAULT NULL::timestamp without time zone, IN in_overgrowth integer DEFAULT NULL::integer, IN in_overgrowth_date timestamp without time zone DEFAULT NULL::timestamp without time zone)
    language plpgsql
as
$$
declare
    river_result proc_result_type;
    voivodeship_result proc_result_type;
    station_result proc_result_type;
    insert_result proc_result_type;
    error_message error_log_type;
begin
    error_message.function_name := 'addMeasurement';
    error_message.error_time := now();

    -- Ensure river exists if provided
    if in_riverName is not null then
        begin
            call addRiver(in_riverName, river_result);
        exception when others then
            error_message.error_message := format('Failed to add river: %s', SQLERRM);
            insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        end;
    end if;

    -- Ensure voivodeship exists if provided
    if in_voivodeshipName is not null then
        begin
            call addVoivodeship(in_voivodeshipName, voivodeship_result);
        exception when others then
            error_message.error_message := format('Failed to add voivodeship: %s', SQLERRM);
            insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        end;
    end if;

    -- Ensure station exists if enough data is provided
    if in_stationName is not null and in_riverName is not null and in_voivodeshipName is not null then
        begin
            call addStation(in_stationID, in_stationName, in_riverName, in_voivodeshipName, station_result);
        exception when others then
            error_message.error_message := format('Failed to add station: %s', SQLERRM);
            insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
            return;
        end;
    end if;

    -- Update station with coordinates if they're available and missing
    if in_latitude is not null and in_longitude is not null then
        begin
            update stations
            set latitude = in_latitude,
                longitude = in_longitude
            where id = in_stationID
              and (latitude is null or longitude is null);
        exception when others then
            error_message.error_message := format('Failed to update coordinates: %s', SQLERRM);
            insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
        end;
    end if;

    -- Insert water level if enough data exists
    if in_level is not null and in_level_time is not null then
        begin
            select * from insert_water_level(
                    in_stationID,
                    in_level,
                    in_level_time,
                    in_flow_rate,
                    in_flow_date
                          ) into insert_result;

            if insert_result.result = false then
                error_message.error_message := format('Water level skipped: %s', insert_result.message);
                insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
            end if;
        exception when others then
            error_message.error_message := format('Failed to insert water level: %s', SQLERRM);
            insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
        end;
    end if;

    -- Insert other measurements if any data exists
    if in_water_temp is not null or in_ice_phenomena is not null or in_overgrowth is not null then
        begin
            insert into other_measurements (
                station_id,
                water_temp, water_temp_date,
                ice_phenomena, ice_phenomena_date,
                overgrowth, overgrowth_date
            ) values (
                         in_stationID,
                         in_water_temp, in_water_temp_date,
                         in_ice_phenomena, in_ice_phenomena_date,
                         in_overgrowth, in_overgrowth_date
                     )
            on conflict do nothing;
        exception when others then
            error_message.error_message := format('Failed to insert other measurements: %s', SQLERRM);
            insert into error_logs values (error_message.function_name, error_message.error_message, error_message.error_time);
        end;
    end if;
end;
$$;

create or replace function get_water_level_deviation(in_station_id integer, in_requested_time timestamp without time zone)
    returns TABLE(nearest_time timestamp without time zone, current_level real, average_level real, deviation real)
    language plpgsql
as
$$
declare
    nearest_record record;
    oldest_time timestamp;
    avg_selected real;
begin
    select time, level
    into nearest_record
    from water_levels
    where station_id = in_station_id
    order by abs(extract(epoch from (time - in_requested_time)))
    limit 1;

    if nearest_record is null then
        raise exception 'No measurements found for station %', in_station_id;
    end if;

    select min(time)
    into oldest_time
    from water_levels
    where station_id = in_station_id
      and time <= nearest_record.time;

    if oldest_time is null then
        raise exception 'No historical data found for station %', in_station_id;
    end if;

    if nearest_record.time - oldest_time >= interval '1 year' then
        select avg(level)
        into avg_selected
        from water_levels
        where station_id = in_station_id
          and time >= nearest_record.time - interval '1 year'
          and time <= nearest_record.time;
    else
        select avg(level)
        into avg_selected
        from water_levels
        where station_id = in_station_id
          and time <= nearest_record.time;
    end if;

    return query
        select
            nearest_record.time,
            nearest_record.level,
            avg_selected,
            nearest_record.level - avg_selected;
end;
$$;

create or replace function get_water_levels_between(in_station_id integer, in_from timestamp without time zone, in_to timestamp without time zone)
    returns TABLE(measurement_time timestamp without time zone, level real)
    language sql
as
$$
select
    wl.time as measurement_time,
    wl.level
from water_levels wl
where wl.station_id = in_station_id
  and wl.time >= in_from
  and wl.time <= in_to
order by wl.time;
$$;


