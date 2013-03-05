package opd

class Price {

    Integer sumprice 
	Date date
	
	
	static hasMany = [prescription: Prescription, transfer:Transfer]
	static belongsTo = [patient: Patient, account:Account]

    static constraints = {
    }
}
