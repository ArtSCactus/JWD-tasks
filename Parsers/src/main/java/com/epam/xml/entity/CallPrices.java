package com.epam.xml.entity;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallPrices", propOrder = {
        "insideNetwork",
        "outsideNetwork",
        "sms",
        "landLine"
})
public class CallPrices {
    @XmlElement(required = true)
    private double insideNetwork;
    @XmlElement(required = true)
    private double outsideNetwork;
    @XmlElement(required = true)
    private double sms;
    @XmlElement(required = true)
    private double landLine;

    public CallPrices(double insideNetwork, double outsideNetwork, double sms, double landLine) {
        this.insideNetwork = insideNetwork;
        this.outsideNetwork = outsideNetwork;
        this.sms = sms;
        this.landLine = landLine;
    }

    public CallPrices() {

    }

    public double getInsideNetwork() {
        return insideNetwork;
    }


    public double getOutsideNetwork() {
        return outsideNetwork;
    }


    public double getSms() {
        return sms;
    }


    public double getLandLine() {
        return landLine;
    }

    public static class Builder {
        private CallPrices newPrices;

        public Builder() {
            newPrices = new CallPrices();
        }

        public Builder withInsideNWPayroll(double payroll) {
            newPrices.insideNetwork = payroll;
            return this;
        }

        public Builder withOutsideNWPayroll(double payroll) {
            newPrices.outsideNetwork = payroll;
            return this;
        }

        public Builder withSMSPayroll(double payroll) {
            newPrices.sms = payroll;
            return this;
        }

        public Builder withLandlinePayroll(double payroll) {
            newPrices.landLine = payroll;
            return this;
        }

        public CallPrices build() {
            return newPrices;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallPrices)) return false;
        CallPrices prices = (CallPrices) o;
        return Double.compare(prices.getInsideNetwork(), getInsideNetwork()) == 0 &&
                Double.compare(prices.getOutsideNetwork(), getOutsideNetwork()) == 0 &&
                Double.compare(prices.getSms(), getSms()) == 0 &&
                Double.compare(prices.getLandLine(), getLandLine()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInsideNetwork(), getOutsideNetwork(), getSms(), getLandLine());
    }

    @Override
    public String toString() {
        return "CallPrices{" +
                "insideNetwork=" + insideNetwork +
                ", outsideNetwork=" + outsideNetwork +
                ", sms=" + sms +
                ", landLine=" + landLine +
                '}';
    }
}
