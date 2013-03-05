package opd

import org.zkoss.zk.grails.composer.*
import java.text.*
import java.util.Locale
import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class DoctorComposer extends GrailsComposer {

    def afterCompose = { window ->
	def patient
	def symptom
	def defsi
	def a
	def slip
	def doctor
	def drugs=""
	def tran
	def ap

	$("#dlogin").on('click',{
	doctor = Doctor.findWhere(username: $("#duser").val(), password:$("#dpass").val())
	if(doctor!=null){
		$("#logoutdoctor").setVisible(true)
		$("#p1").setSelected(true)
		$("#gbf").setVisible(false)
		$("#gbl").setVisible(false)

		$("#p1").setVisible(true)
		$("#p2").setVisible(true)
		$("#p3").setVisible(true)
		$("#p4").setVisible(true)
		$("#p5").setVisible(true)



	$("row > #phn").on('change',{

		patient = Patient.findByIdcard($("row > #phn").val())
	if(patient!=null){
		 $('row > #psex').val(patient.sex)
		 $('row > #pname').val(patient.firstname+" "+patient.lastname)
		 $('row > #ptel').val(patient.tel)
		DateFormat formatter = new SimpleDateFormat('E, dd MMM yyyy', Locale.ENGLISH)
		 $('row > #pbirth').val(formatter.format(patient.birthday))
		 $('row > #pwelfare').val(patient.welfare)
		 $('row > #pwork').val(patient.work)
		 
		 

		symptom = Symptom.findByPatient(patient,[sort:'id',order:'desc'])
		defsi = patient.symptoms.findAll()
		 $('row > #psymptom').val(symptom.description)
		 $('row > #ptemperature').val(patient.temperature)
		 $('row > #pheight').val(patient.height)
		 $('row > #pweight').val(patient.weight)

		 if(patient.bmi!=null){
		 $('#pbmif > #pbmi').val(patient.bmi)
		 Float bmiuse=patient.bmi.toFloat();
		 if(bmiuse>18.5&&bmiuse<=23.5){
		 	$('row > #pbmif')[0].style="background-color:#CCFB5D;"
		 }else if(bmiuse>23.5){
			$('row > #pbmif')[0].style="background-color:#FDD017;"
		 }else{
		 	$('row > #pbmif')[0].style="background-color:#E9CFEC;"
		 }
}


	    	if(patient.allergic!=null){
	    		$('listbox > listitem').detach()
	    		$('#box').append {
	    			listitem {
						listcell(label: patient.allergic,style:"color:red;")
	
					}
	     		}
	     	}

	    	if(symptom.diagnose!=null){
	    		$('listbox > #ddiag').detach()
	    	}

	    	Integer i =1;

	    	for(c in defsi){
	    		if(c.diagnose!=null && i<=10){
	    			i++
	    		$('#boxdiag').append {
	    			listitem {
						listcell(label: c.description,style:"color:green;")
						listcell(label: c.diagnose,style:"color:green;")
					}
	     		}
	     	}
	     	}
	     	$("#btnDiagnoseC").on('click',{
	     		$("#txtDiagnose").val('')
	     		})
	     	  	$("#btnDiagnoseS").on('click',{
	     		
	     			symptom.diagnose=$("vbox > #txtDiagnose").val()
	     			symptom.save()
	     			if(symptom.diagnose!=null){
	    		$('#boxdiag > listitem').detach()
	    	}
	    	Integer j =1;

	    	for(c in defsi){
	    		if(c.diagnose!=null && j<=10){
	    			j++
	    		$('#boxdiag').append {
	    			listitem {
						listcell(label: c.description,style:"color:green;")
						listcell(label: c.diagnose,style:"color:green;")
					}
	     		}
	     	}
	     	}

	     		})



	$("#sendorappoint").on('click',{
    if($("#appp[checked=true]")){
	    	
    	($("vbox > #as")).setVisible(true)
	    }else{
	   ($("vbox > #as")).setVisible(false)
	    }	
		})	


	$("#btnAs").on('click',{
		
		a = MedicineInfo.findByName($("#combo").text())
		if(a!=null){
  		$('#boxde').append {
	    			listitem {
	    				listcell(label:a.id)
						listcell(label:$("#combo").text())
						listcell(label:$("#qdrug").val())
						drugs+=$("#combo").text()+"/"+$("#qdrug").val()+"|"
					}
	     		}
	}
	


	})

$("#sd").on('click',{
	if($("#sendp").isSelected() && $("#txtsendappoint").val()!=""){
	tran = new Transfer(comment:$("#txtsendappoint").val(), doctor:doctor, patient:patient)//.findByPatient(patient,[sort:'id',order:"desc"])
	//if(tran!=null){
	//tran.comment = $("#txtsendappoint").val()
	//tran.doctor = doctor
	//tran.patient = patient
	tran.save()
	alert("ส่งตัวเรียบร้อยแล้ว")

	}else if($("#txtsendappoint").val()!=""){
		ap = new Appointment(patient:patient,date:$("#db1").getValue(),time:$("#tb1").getValue(),comment:$("#txtsendappoint").val())
		if(ap!=null){
		if(ap.validate()){
			ap.save()
			}else{
				println ap
			}

		alert("ส่งเข้าระบบนัดพบแพทย์เรียบร้อยแล้ว")
}
	}
	})


    }else{

    	 $('row > #psex').val("null")
		 $('row > #pname').val("null")
		 $('row > #ptel').val("null")
		 $('row > #pbirth').val("null")
		 $('row > #pwelfare').val("null")
		 $('row > #pwork').val("null")
	
		 $('row > #psymptom').val("null")
		 $('row > #ptemperature').val("null")
		 $('row > #pheight').val("null")
		 $('row > #pweight').val("null")
		 $('#pbmif > #pbmi').val("null")
	
	    $('#boxdiag > listitem').detach()
	    $("#box > listitem").detach()
	    		$('#box').append {
	    			listitem {
						listcell(label: "ไม่มีรายการแพ้ยา")
					}
	     		}
    }

    $("#readC").on('click',{
	if($("#readC").isChecked()){
	($("#btnAs"))[0].visible = true
	}else{
		($("#btnAs"))[0].visible = false

	}

	})

	$("#preid").val(""+Prescription.count())

	def med = MedicineInfo.findAll()
		for(c in med){
				$("#combo").append{
				comboitem(label: c.name,style:"color:orange;",visible:true)
			}
	}
	//$("#combo").setAutocomplete(false) // Autocomplete
	$("#combo").on('change',{
		for(c in med){
		if((c.name).startsWith($("#combo").text())){
				$("#combo > comboitem[label='$c.name']").setVisible(true)
				}else{
				$("#combo > comboitem[label='$c.name']").setVisible(false)
				}

		}
		})
	
	

$("#slip").on('click',{
		if(drugs!=""){
		def sliplist = new Prescription(comment:drugs, doctor:doctor, patient:patient)
		sliplist.save()
		
		}
	})
})


}else{
	alert("กรุณาใช้ Username หรือ Password ที่ถูกต้อง!")
}
	
		})
}
}