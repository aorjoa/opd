package opd

class Pharmacist {
	String firstname
	String lastname
	String user
	String password

	static hasMany = [prescription: Prescription]
    static constraints = {
    	user unique:true
    }
}
