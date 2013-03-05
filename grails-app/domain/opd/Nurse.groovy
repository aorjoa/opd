package opd

class Nurse {
	String firstname
	String lastname
	String user
	String password

	static hasMany = [symptoms:Symptom , transfer:Transfer]

    static constraints = {
    	user unique:true
    	transfer nullable:true
    }
}
