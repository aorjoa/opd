package opd

import org.zkoss.zk.grails.composer.*
import org.zkoss.zul.Messagebox
import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen
import org.zkoss.zk.ui.event.Event

class SaveSymptomComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here
		if (!session['user'])
			redirect(uri:'/registers/login.zul')
		else {
			window.visible = true
			
			$("#logout").on("click", {
				session.user = null
				redirect(uri:'/index.zul')
			})

			def patient = session.savePatient
			def nurse = session.user

	        $("#firstname").val(patient.firstname)
			$("#lastname").val(patient.lastname)
			$("#idcard").val(patient.idcard)
			
			if (patient.sex.equals("Female"))
				$("#female").setChecked(true)	
			else
				$("#male").setChecked(true)
			$("#birthday").val(patient.birthday)
			$("#tel").val(patient.tel)
			$("#race").val(patient.race)
			$("#nationality").val(patient.nationality)
			$("#religion").val(patient.religion)

			$("#work").setSelectedItem($("#work comboitem[label='${patient.work}']")[0])
			

			$("radio[label='${patient.allergic}']").setChecked(true)
			$("radio[label='${patient.welfare}']").setChecked(true)

			$("radio").each { it.setDisabled(true)}
			$("#work")[0].setDisabled(true)
			$("textbox").each {
				if (it.id != 'symptom') {
					it.setDisabled(true)
				}
			}
			$("datebox")[0].setDisabled(true)

	        $("#logonName").val(session.user.user)
			$("#today").val(new Date())



			$("#saveSymptom").on("click",{
				def symptom = new Symptom(description:$("#symptom").val(), nurse:nurse, patient:patient , date:new Date())
				if (symptom.validate()) {
					symptom.save()
					Messagebox.show("save finised", null, Messagebox.OK, Messagebox.INFORMATION,
	                    new org.zkoss.zk.ui.event.EventListener() {
	                        public void onEvent(Event evt) throws InterruptedException {
	                            if (evt.getName().equals("onOK")) {
	                            	session.symptom = symptom
	                                redirect(uri:'/registers/printVisitSlip.zul')
	                            }
	                        }
	                    }
	                )

				} else {
					println symptom.errors
					alert('Error!!!')
				}
					
			})

		}

    }
}
