package opd

class Appointment {
	Date date
	Date time
	String comment
	
	static belongsTo = [patient:Patient]
    static constraints = {
    	date nullable:true
    	time nullable:true
    	comment nullable:true
    }
}