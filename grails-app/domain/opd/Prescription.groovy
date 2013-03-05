package opd

class Prescription {
	String comment
	//Integer medicinevalue
	Integer total

	static belongsTo = [doctor:Doctor,pharmacist:Pharmacist,patient:Patient]
	static hasMany = [medicineInfo:MedicineInfo]

    static constraints = {
    	comment nullable:true
        doctor nullable:true
        patient nullable:true
        //medicinevalue nullable:true
        pharmacist nullable:true
        total nullable:true
    }
}
