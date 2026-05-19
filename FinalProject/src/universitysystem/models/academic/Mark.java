package universitysystem.models.academic;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Mark implements Comparable<Mark>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public Mark() {
    }

    /**
     * Full constructor
     */
    public Mark(double att1, double att2, double finalExam) {
        this.att1 = att1;
        this.att2 = att2;
        this.finalExam = finalExam;
    }

    /**
     * 
     */
    private double att1;

    /**
     * 
     */
    private double att2;

    /**
     * 
     */
    private double finalExam;

    public double getAtt1() {
        return att1;
    }

    public void setAtt1(double att1) {
        this.att1 = att1;
    }

    public double getAtt2() {
        return att2;
    }

    public void setAtt2(double att2) {
        this.att2 = att2;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }




    /**
     * 
     */
    public double calculateFinal() {
        return att1 * 0.3 + att2 * 0.3 + finalExam * 0.4;
    }

    @Override
    public int compareTo(Mark other) {
        return Double.compare(this.calculateFinal(), other.calculateFinal());
    }

    @Override
    public String toString() {
        return "Mark{" +
                "att1=" + att1 +
                ", att2=" + att2 +
                ", finalExam=" + finalExam +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark mark = (Mark) o;
        return Double.compare(mark.att1, att1) == 0 &&
                Double.compare(mark.att2, att2) == 0 &&
                Double.compare(mark.finalExam, finalExam) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(att1, att2, finalExam);
    }

}
