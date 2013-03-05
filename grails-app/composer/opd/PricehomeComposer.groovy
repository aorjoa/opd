package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class PricehomeComposer extends GrailsComposer {

    def afterCompose = { window ->
        
    	if (!session['user'])
			redirect(uri:'/index.zul')
		else{
			window.visible = true	
		}
		$('#nameBuncee').val(session.user.fname+" "+session.user.lname)
		$("#today").val(new Date())

		$('#IDPnuknanSearch').val(session.user.userid)
		$('#namePanukngan').val(session.user.fname+"  "+session.user.lname)
		$('#livePanukngan').val(session.user.address)
		$('#telePhPanukngan').val(session.user.phone)
		Integer sum=0
		def ti = new Date()
		Integer num1=0

		$("#logOut").on("click", {
				session.user = null
				redirect(uri:'/index.zul')
		})

		$('#searchPoopuy').on('click',{
			
			def patient = Patient.findByIdcard($("#numberNamePoopuy1").val())
        	if (patient) {
        		$('#namePoopuy').val(patient.firstname)
				$('#lastnamePoopuy').val(patient.lastname)
				$('#numberNamePoopuy2').val(patient.idcard)
				$('#numberPakunsungkom').val(patient.welfare)
        	}
		})

		$('#clear').on('click',{
			$('#numberNamePoopuy1').setText(null)
			$('#namePoopuy').setText(null)
			$('#lastnamePoopuy').setText(null)
			$('#numberNamePoopuy2').setText(null)
			$('#numberPakunsungkom').setText(null)
			$('#Ya').setText(null)
			$('#moneyYa').setText(null)
			$('#nameDoctor').setText(null)
			$('#IDnamePanuknan').setText(null)
			$('#moneySum').setText(null)
			$('#saveData').setVisible(true)

		})

		$('#searchMonyPoopuy').on('click',{
				sum=0
				def patient = Patient.findByIdcard($("#numberNamePoopuy1").val())

				if (patient) {
					String s = patient.symptoms.nurse.transfer.type[0]
					if(s=="[X-ray]"){
						$('#xray').setChecked(true)
						sum = sum+500

						def mxray
						session.mxray=500
					}
					if(s=="[Lab]"){
						$('#lab').setChecked(true)
						sum = sum+1000
						def mlab
						session.mlab=1000
					}
					$('#Ya').val(patient.prescription.id[0])
					$('#moneyYa').val(patient.prescription.total)
					$('#nameDoctor').val(patient.symptoms.doctor.firstname[0]+"  "+patient.symptoms.doctor.lastname[0])
					$('#moneyDoctor').val(200)
					$('#IDnamePanuknan').val(session.user.fname+"  "+session.user.lname)
					sum = sum+200+(patient.prescription.total)
					$('#moneySum').val(sum)
					$('#B').setSelected(true)

				}else {
					$('#numberNamePoopuy1').setText(null)
					alert("ไม่มีข้อมูล")
				}
		})


		$('#saveData').on('click',{
			def patient = Patient.findByIdcard($("#numberNamePoopuy1").val())
			def price = new Price(sumprice: sum,date: new Date(), patient:patient, account:session.user)
			if(price.validate()){
        			price.save()
        			$("grid rows").append{
						row(){
							label(value:price.date)
							label(value:session.user.fname+" "+session.user.lname)
							label(value:patient.firstname+" "+patient.lastname)
							label(value:price.sumprice+" บาท")
							label(value:patient.tel)
						}
					}
					alert("บันทึกสำเร็จ")
					sum=0
					$('#saveData').setVisible(false)
      		}


		})

		$('#printData').on('click',{
			if($("#numberNamePoopuy1").val()!=null){
				def namepp
				session.namepp = $("#numberNamePoopuy1").val()
				redirect(uri:'/finance/priceslip.zul')
			}else { alert("ใส่ข้อมูลผู้ป่วย") }

		})
        
    
    }
}
