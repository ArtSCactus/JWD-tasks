package com.epam.xml.entity;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tariff", propOrder = {
        "callPrices",
        "parameters"
})
public class Tariff {
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    private String name;
    @XmlAttribute(required = true)
    private String operator;
    @XmlAttribute(required = true)
    private double payroll;
    @XmlElement(required = true)
    private CallPrices callPrices;
    @XmlElement(required = true)
    private Parameters parameters;

    public Tariff(String name, String operator, double payroll, CallPrices callPrices, Parameters parameters) {
        this.name = name;
        this.operator = operator;
        this.payroll = payroll;
        this.callPrices = callPrices;
        this.parameters = parameters;
    }

    public Tariff() {
    }

    public String getName() {
        return name;
    }

    public String getOperator() {
        return operator;
    }

    public double getPayroll() {
        return payroll;
    }

    public void setPayroll(double payroll) {
        this.payroll = payroll;
    }

    public CallPrices getCallPrices() {
        return callPrices;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public static class Builder {
        private Tariff newTariff;

        public Builder() {
            newTariff = new Tariff();
        }

        public Builder withName(String name) {
            newTariff.name = name;
            return this;
        }

        public Builder withOperator(String name) {
            newTariff.operator = name;
            return this;
        }

        public Builder withPayroll(double payroll) {
            newTariff.payroll = payroll;
            return this;
        }

        public Builder withParameters(Parameters parameters) {
            newTariff.parameters = parameters;
            return this;
        }

        public Builder withCallPrices(CallPrices prices) {
            newTariff.callPrices = prices;
            return this;
        }

        public Tariff build() {
            return newTariff;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tariff)) return false;
        Tariff tariff = (Tariff) o;
        return Double.compare(tariff.getPayroll(), getPayroll()) == 0 &&
                getName().equals(tariff.getName()) &&
                getOperator().equals(tariff.getOperator()) &&
                getCallPrices().equals(tariff.getCallPrices()) &&
                getParameters().equals(tariff.getParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getOperator(), getPayroll(), getCallPrices(), getParameters());
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "name='" + name + '\'' +
                ", operator='" + operator + '\'' +
                ", payroll=" + payroll +
                ", callPrices=" + callPrices +
                ", parameters=" + parameters +
                '}';
    }
}
