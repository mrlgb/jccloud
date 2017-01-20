package edu.hfuu.jccloud.tableUI.student;

/**
 * Created by mrlgb on 2017/1/2.
 */

public class EventStudent {
    private Student student;
    public EventStudent(Student student){
        this.student = student;
    }
    public Student getEventStuduent() {
        return student;
    }
}
