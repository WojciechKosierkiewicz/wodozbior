package com.example.wodozbior.service;

import com.example.wodozbior.dto.hydrodata.HydroStationFullDto;
import com.example.wodozbior.entity.*;
import com.example.wodozbior.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HydroDatabaseSaveService {

    private final VoivodeshipRepository voivodeshipRepo;
    private final RiverRepository riverRepo;
    private final StationRepository stationRepo;
    private final WaterLevelRepository waterLevelRepo;
    private final OtherMeasurementRepository otherMeasurementRepo;

    public HydroDatabaseSaveService(VoivodeshipRepository voivodeshipRepo,
                                    RiverRepository riverRepo,
                                    StationRepository stationRepo,
                                    WaterLevelRepository waterLevelRepo,
                                    OtherMeasurementRepository otherMeasurementRepo) {
        this.voivodeshipRepo = voivodeshipRepo;
        this.riverRepo = riverRepo;
        this.stationRepo = stationRepo;
        this.waterLevelRepo = waterLevelRepo;
        this.otherMeasurementRepo = otherMeasurementRepo;
    }

    @Transactional
    public void saveAll(List<HydroStationFullDto> dtos) {
        for (HydroStationFullDto dto : dtos) {
            River river = riverRepo.findByName(dto.getRiver())
                    .orElseGet(() -> riverRepo.save(new River(null, dto.getRiver())));

            Voivodeship voivodeship = voivodeshipRepo.findByName(dto.getVoivodeship())
                    .orElseGet(() -> voivodeshipRepo.save(new Voivodeship(null, dto.getVoivodeship())));

            Station station = stationRepo.findByName(dto.getStationName())
                    .orElseGet(() -> {
                        Station s = new Station();
                        s.setName(dto.getStationName());
                        s.setRiver(river);
                        s.setVoivodeship(voivodeship);
                        s.setLatitude(dto.getLat());
                        s.setLongitude(dto.getLon());
                        return stationRepo.save(s);
                    });

            for (HydroStationFullDto.MeasurementDto m : dto.getMeasurements()) {
                if (m.getWaterLevel() != null && m.getWaterLevelDate() != null) {
                    boolean exists = waterLevelRepo.existsByStationAndTime(station, m.getWaterLevelDate());
                    if (!exists) {
                        WaterLevel wl = new WaterLevel();
                        wl.setStation(station);
                        wl.setLevel(m.getWaterLevel().floatValue());
                        wl.setTime(m.getWaterLevelDate());
                        wl.setFlowRate(m.getFlow() != null ? m.getFlow().floatValue() : null);
                        wl.setFlowDate(m.getFlowDate());
                        waterLevelRepo.save(wl);
                    }
                }

                if (m.getTemperature() != null || m.getIcePhenomenon() != null || m.getOvergrownPhenomenon() != null) {
                    boolean exists = otherMeasurementRepo.existsByStationAndWaterTempDateAndIcePhenomenaDateAndOvergrowthDate(
                            station,
                            m.getTemperatureDate(),
                            m.getIcePhenomenonDate(),
                            m.getOvergrownPhenomenonDate()
                    );
                    if (!exists) {
                        OtherMeasurement om = new OtherMeasurement();
                        om.setStation(station);
                        om.setWaterTemp(m.getTemperature() != null ? m.getTemperature().floatValue() : null);
                        om.setWaterTempDate(m.getTemperatureDate());
                        om.setIcePhenomena(parseInt(m.getIcePhenomenon()));
                        om.setIcePhenomenaDate(m.getIcePhenomenonDate());
                        om.setOvergrowth(parseInt(m.getOvergrownPhenomenon()));
                        om.setOvergrowthDate(m.getOvergrownPhenomenonDate());
                        otherMeasurementRepo.save(om);
                    }
                }
            }
        }
    }

    private Integer parseInt(String s) {
        try {
            return s != null ? Integer.parseInt(s) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
