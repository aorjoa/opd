package opd

class Symptom {

	String description
	String diagnose
	Date date
	static belongsTo = [nurse:Nurse , patient:Patient, doctor:Doctor]
    static constraints = {
    	diagnose nullable:true
    	doctor nullable:true
    }
}
