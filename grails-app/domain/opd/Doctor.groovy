package opd

class Doctor {

	String firstname
	String lastname
	String username
	String password


	String iddoctor
	String	status  //ตำแหน่ง
	String	section	//แผนก
	String	room
	String	tel

	static hasMany = [
	prescriptions: Prescription, 
	transfers:Transfer, 
	appointments:Appointment, 
	symptoms:Symptom
	]

    static constraints = {
		username unique:true

 		iddoctor nullable:true
		status  nullable:true//ตำแหน่ง
		section	nullable:true //แผนก
		room	nullable:true
		tel nullable:true
    }
}
