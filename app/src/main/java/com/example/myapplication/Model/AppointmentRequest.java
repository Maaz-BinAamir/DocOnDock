package com.example.myapplication.Model;

public class AppointmentRequest {
    String patientUid;
    String age;
    String gender;
    String briefProblemDescription;
    String doctorSpeciality;

    public AppointmentRequest(String patientUid, String age, String gender, String briefProblemDescription, String doctorSpeciality) {
        this.patientUid = patientUid;
        this.age = age;
        this.gender = gender;
        this.briefProblemDescription = briefProblemDescription;
        this.doctorSpeciality = doctorSpeciality;
    }

    public AppointmentRequest() {
    }

    public String getPatientUid() {
        return patientUid;
    }

    public void setPatientUid(String patientUid) {
        this.patientUid = patientUid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBriefProblemDescription() {
        return briefProblemDescription;
    }

    public void setBriefProblemDescription(String briefProblemDescription) {
        this.briefProblemDescription = briefProblemDescription;
    }

    public String getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        this.doctorSpeciality = doctorSpeciality;
    }
}
