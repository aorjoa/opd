package opd

class Account {
	String userid
	String password
	String fname
	String lname
	String address
	String phone
	
	static hasMany = [price: Price]
    static constraints = {
    }
}
