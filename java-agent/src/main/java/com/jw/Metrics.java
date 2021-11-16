package com.jw;

import io.micrometer.core.instrument.*;

import java.util.ArrayList;
import java.util.List;

public class Metrics {

    private final String name;

    private final String description;

    private final String unit;

    private final List<Sample> measurements;

    private final List<Tag> availableTags;

    public Metrics(String name, String description, String unit, List<Sample> measurements, List<Tag> availableTags) {
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.measurements = measurements;
        this.availableTags = availableTags;
    }

    public static final class Sample {

        private final Statistic statistic;

        private final Double value;

        Sample(Statistic statistic, Double value) {
            this.statistic = statistic;
            this.value = value;
        }

        public Statistic getStatistic() {
            return this.statistic;
        }

        public Double getValue() {
            return this.value;
        }


        @Override
        public String toString() {
            return "MeasurementSample{statistic=" + this.statistic + ", value=" + this.value + '}';
        }
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", measurements=" + measurements +
                ", availableTags=" + availableTags +
                '}';
    }

    public static List<Metrics> build(MeterRegistry registry) {
        List<Metrics> metricsList = new ArrayList<>();
        List<Meter> meters = registry.getMeters();
        for (Meter meter : meters) {
            Metrics metrics = build(meter);
            metricsList.add(metrics);
        }
        return metricsList;
    }

    public static Metrics build(Meter meter) {
        String name = meter.getId().getName();
        String baseUnit = meter.getId().getBaseUnit();
        String description = meter.getId().getDescription();
        Iterable<Measurement> measure = meter.measure();
        List<Sample> measurements = new ArrayList<>();
        for (Measurement measurement : measure) {
            Sample sample = new Sample(measurement.getStatistic(), measurement.getValue());
            measurements.add(sample);
        }
        List<Tag> tags = meter.getId().getTags();
        return new Metrics(name, description, baseUnit, measurements, tags);
    }

}
