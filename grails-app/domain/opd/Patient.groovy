package opd

class Patient {

    String firstname
	String lastname
	String idcard
	String sex
	Date birthday
	String tel
	String race
	String nationality
	String religion
	String work
	String welfare //สิทธิ์การรักษา
	String allergic //อาการแพ้ยา


	Integer queue 
	String temperature
	String height
	String weight
	String bmi
	String heartrate
	String lbloodpressure
	String hbloodpressure
	String respiratoryrate //อัตราการหายใจ

	static hasMany = [symptoms:Symptom, 
	price:Price, appointment:Appointment,prescription:Prescription, transfer: Transfer]


    static constraints = {
    	
    	allergic nullable : true
    	queue nullable : true
    	temperature nullable : true
    	height nullable : true
    	weight nullable : true
    	bmi nullable : true
    	heartrate nullable : true
    	lbloodpressure nullable : true
    	hbloodpressure nullable : true
		respiratoryrate nullable : true

	}
}
