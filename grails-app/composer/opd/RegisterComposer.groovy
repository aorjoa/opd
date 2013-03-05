package opd

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.Executions
import org.zkoss.zul.Messagebox

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen
import org.zkoss.zk.ui.event.Event

class RegisterComposer extends GrailsComposer {

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

            $("#logonName").val(session.user.user)
            $("#today").val(new Date())


            $("#save").on("click", {
            	def newPatient = new Patient()
            	newPatient.firstname = $("#firstname").val()
            	newPatient.lastname = $("#lastname").val()
            	newPatient.idcard = $("#idcard").val()
            	newPatient.birthday = $("#birthday").val()
            	newPatient.tel = $("#tel").val()
            	newPatient.race = $("#race").val()
            	newPatient.nationality = $("#nationality").val()
            	newPatient.religion = $("#religion").val()
            	newPatient.work = $("#work")[0].getSelectedItem().getLabel()

            	def allradio = $('radio')
            	def sexRadiogroup = $("#sex")[0]
            	def welfareRadiogroup = $("#welfare")[0]
            	def allergicRadiogroup = $("#allergic")[0]

            	allradio.each{ r ->
            		if(r.getRadiogroup().equals(sexRadiogroup)){
            			if (r.isSelected()){
            				newPatient.sex = r.label
            			}
            		}

            		if (r.getRadiogroup().equals(welfareRadiogroup)) {
                        if (r.isSelected()) {
                            newPatient.welfare = r.label;
                        }
                    }

                    if (r.getRadiogroup().equals(allergicRadiogroup)){
                    	if(r.isSelected()){
                    		newPatient.allergic = r.label
                    	}
                    }
            	}

            	newPatient.save()
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
