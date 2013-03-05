package opd

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.Messagebox
import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class CreateNurseComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here

        if (!session['user'] || !session['user'].user.equals('admin'))
			redirect(uri:'/registers/login.zul')
		else {
			window.visible = true

			$("#logout").on("click", {
				session.user = null
				redirect(uri:'/index.zul')
			})

			// show user name & date
			$("#logonName").val(session.user.user)
			$("#today").val(new Date())

			$("#save").on("click",{
				def nurse = new Nurse(firstname:$("#firstname").val(), lastname:$("#lastname").val(), user:$("#username").val(), password:$("#password").val())

				/*if (nurse.validate()) {
					nurse.merge()
					redirect(uri:'/registers/searchNurse.zul')
				} else {
					alert("Error !")
				}*/

				nurse.merge()

				Messagebox.show("save finised", null, Messagebox.OK, Messagebox.INFORMATION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event evt) throws InterruptedException {
                            if (evt.getName().equals("onOK")) {
                                redirect(uri:'/registers/searchNurse.zul')
                            }
                        }
                    }
                )

			})
		}
    }
}
