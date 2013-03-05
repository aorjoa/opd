package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class ScreeningComposer extends GrailsComposer {

    def afterCompose = { window ->
    	  	
    	if (!session['user'])
			redirect(uri:'/login2.zul')

		else {
			window.visible = true

			def patient = session.editPatient
			$("#logout").on("click", {
				session.user = null
				patient = null
				session.editPatient = null
				redirect(uri:'/index.zul')
			})

			$("#btnsearch").on("click", {
				patient = null
				session.editPatient = null
				redirect(uri:'/screening/searchInScreening.zul')
			})
			
			def name = "${patient.firstname}   ${patient.lastname}"	
			$('#labname').val(name)
			def now = new Date()
			def	age	= now[Calendar.YEAR]-patient.birthday[Calendar.YEAR]
			$('#labage').val(age)
			$('#labidcard').val(patient.idcard)

			$('#idcard').val(patient.idcard)
			$('#name').val(name)
			$('#sex').val(patient.sex)
			$('#race').val(patient.race)
			$('#religion').val(patient.religion)

			def symptom	= Symptom.findByPatient(patient, [sort:'id', order:'desc'])
			$('#txtdescription').val(symptom.description)

		
			//Doctor  combobox
			def doctors = Doctor.list()
			$('#comdoctor').getChildren().clear() //remove all children
			doctors.each { doctor ->

				$('#comdoctor').append{
	    			comboitem(label:"${doctor.firstname}   ${doctor.lastname}", value:"${doctor.id}")
	    		}	
			}

		$('#seldoctor').on('click',{

			//if($('#seldoctor').getItems()!=null){
				def doctor = Doctor.get($("#comdoctor").getSelectedItem().value.toLong())
				//println doctor
				 

				
				$('#iddoctor').val(doctor.id)
				$('#namedoctor').val("${doctor.firstname}   ${doctor.lastname}")
				$('#status').val(doctor.status)
				$('#section').val(doctor.section)
				$('#room').val(doctor.room)
				$('#tel').val(doctor.tel)

				symptom.doctor=doctor
			//}
			//else{
			//alert("กรุณาเลือกแพทย์")
			//}


		})
		

		$('#btnsaveall').on('click',{
			
			symptom.description =	$('#txtdescription').val()
			
			patient.weight		=	$('#txtweight').val()
			patient.height 		=	$('#txtheight').val()
			patient.heartrate		=	$('#txtheartrate').val()
			patient.temperature 	=	$('#txttemperature').val()
			patient.lbloodpressure	=	$('#txtlbloodpressure').val()
			patient.hbloodpressure 	=	$('#txthbloodpressure').val()
			patient.respiratoryrate =	$('#txtrespiratoryrate').val()
			patient.bmi				=	$('#txtbmi').val()

			if (($("#iddoctor").val()!="---")&&($('#txtweight').val()!="")&&($('#txtheight').val()!="")
				&&($('#txtheartrate').val()!="")&&($('#txttemperature').val()!="")
				&&($('#txtlbloodpressure').val()!="")
				&&($('#txthbloodpressure').val()!="")
				&&($('#txtrespiratoryrate').val()!="")
				&&($('#txtbmi').val()!="")){

				patient.save()	
				symptom.save()
				alert("success")

			}else{
				alert("กรุณากรอกข้อมูลให้ครบถ้วน")				
			}
			
     	})
		}
    }
}


		

		
		//def	doctor  = Dortor.list()
		//def symptom	= Symptom.list()
		
// updat info patient
		
		

		

		


			
    		
			/* def firstname	=	
			 def lastname	=	
			 def idcard		=	
			 def sex		=	
			 def birthday	=	
			 def tel		=	
			 def race		=	
			 def nationality	=	
			 def religion	=	
			 def work		=	
			 def welfare	=	
			 def allergic	=	
			*/

			// def queues 		=
			
				
			
				
		

		/*def p = new Patient(
		 	 firstname	:	"d",
			 lastname	:	"s",
			 idcard		:	"1",
			 sex		:	"1",
			 birthday	:	new Date(),
			 tel		:	"1",
			 race		:	"1",
			 nationality	:	"1",
			 religion	:	"1",
			 work		:	"1",
			 welfare	:	"1", //สิทธิ์การรักษา
			 allergic	:	"1", //อาการแพ้ยา


			 queue 		:	1,
			 temperature :	"1",
			 height 	:	"1",
			 weight 	:	"1",
			 bmi 		:	"1",
			 heartrate 	:	"1",
			 lbloodpressure 	:	"1",
			 hbloodpressure 	:	"1",
			 respiratoryrate  	:	"1"
					)
			*/

			/*
			if (patient.validate()) {
				patient.save()	
				alert('success')
			} else {
				println p.errors
			}
			
*/		
/*

	$('#save').on('click',{	

		def names = $('#name')[0].text
		def weights = $('#weight')[0].value
		def statures = $('#stature')[0].value
		def temperatures = $('#temperature')[0].value
		def pulseRates = $('#pulseRate')[0].value
	def p = new Patient(
		 	name: 	names,
			weight: 	weights,
			stature: 	statures,
			temperature:	temperatures,
			pulseRate: 	pulseRates
			)
			p.save()
}
*/

