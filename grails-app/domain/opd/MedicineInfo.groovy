package opd

class MedicineInfo {
		String name
		String type 
		Integer priceperunit

	static belongsTo = [prescription: Prescription]
    static constraints = {
    	prescription nullable:true
    }
}
