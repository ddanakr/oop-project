package universitysystem.models.academic;

import universitysystem.models.DateTime;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Lesson {

    /**
     * Default constructor
     */
    public Lesson() {
    }

    /**
     * 
     */
    private Course course;

    /**
     * 
     */
    private LessonType lessonType;

    /**
     * 
     */
    private DateTime dateTime;

    /**
     * 
     */
    private  String room;
    
    
    public Lesson(Course course, LessonType lessonType, DateTime dateTime, String room) {
        this.course = course;
        this.lessonType = lessonType;
        this.dateTime = dateTime;
        this.room = room;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Lesson{course=" + (course == null ? null : course.getCourseCode()) + ", lessonType=" + lessonType
                + ", dateTime=" + dateTime + ", room='" + room + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(course == null ? null : course.getCourseCode(),
                lesson.course == null ? null : lesson.course.getCourseCode())
                && lessonType == lesson.lessonType
                && Objects.equals(dateTime, lesson.dateTime)
                && Objects.equals(room, lesson.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course == null ? null : course.getCourseCode(), lessonType, dateTime, room);
    }


}
