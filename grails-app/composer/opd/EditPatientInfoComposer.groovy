package opd

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.Messagebox
import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class EditPatientInfoComposer extends GrailsComposer {

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

			def patient = session.editPatient

			session.editPatient = null

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

	        $("#logonName").val(session.user.user)
			$("#today").val(new Date())

			$("#save").on("click", {
            	patient.firstname = $("#firstname").val()
            	patient.lastname = $("#lastname").val()
            	patient.idcard = $("#idcard").val()
            	patient.birthday = $("#birthday").val()
            	patient.tel = $("#tel").val()
            	patient.race = $("#race").val()
            	patient.nationality = $("#nationality").val()
            	patient.religion = $("#religion").val()
            	patient.work = $("#work")[0].getSelectedItem().getLabel()

            	def allradio = $('radio')
            	def sexRadiogroup = $("#sex")[0]
            	def welfareRadiogroup = $("#welfare")[0]
            	def allergicRadiogroup = $("#allergic")[0]

            	allradio.each{ r ->
            		if(r.getRadiogroup().equals(sexRadiogroup)){
            			if (r.isSelected()){
            				patient.sex = r.label
            			}
            		}

            		if (r.getRadiogroup().equals(welfareRadiogroup)) {
                        if (r.isSelected()) {
                            patient.welfare = r.label;
                        }
                    }

                    if (r.getRadiogroup().equals(allergicRadiogroup)){
                    	if(r.isSelected()){
                    		patient.allergic = r.label
                    	}
                    }
            	}

            	patient = patient.merge()
                Messagebox.show("save finised", null, Messagebox.OK, Messagebox.INFORMATION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event evt) throws InterruptedException {
                            if (evt.getName().equals("onOK")) {
                                redirect(uri:'/registers/searchPatient.zul')
                            }
                        }}
                )
                
            })

		}
    }


}
