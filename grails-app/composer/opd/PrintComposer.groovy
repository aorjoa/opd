package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.Messagebox

class PrintComposer extends GrailsComposer {

    def afterCompose = { window ->
        if (!session['user'])
			redirect(uri:'/medicine/medicineLogin.zul')
		else {
			window.visible = true

			$("#lbUser").val(session.user.firstname + " " + session.user.lastname)

			$("#btnLogout").on("click", {
				session.user = null
				redirect(uri:'/index.zul')
			})

			def sticker = session.prescriptionInfo
			def patient = session.patient
			def phar = session.user

			def pre = Prescription.findByPatient(patient)

			pre = pre.comment
			def nameAndValue = sticker.split("/")
			def name = nameAndValue[0]
			def medicinevalue = nameAndValue[1]
			def medicineInfo = MedicineInfo.findByName(name)
			def type = medicineInfo.type
			def doctor = Prescription.findAllByComment(pre)
			
			$("#lbName").val(patient.firstname + " " + patient.lastname)
			$("#lbMedName").val(name)
			$("#lbDocName").val(doctor.doctor.firstname[0] + " " + doctor.doctor.lastname[0])
			$("#txtPhar").val(phar.firstname + " " + phar.lastname)

			$("#btnPrint").on("click", {

				Messagebox.show("Print สำเร็จ", null, Messagebox.OK, Messagebox.INFORMATION,
	                    new org.zkoss.zk.ui.event.EventListener() {
	                        public void onEvent(Event evt) throws InterruptedException {
	                            if (evt.getName().equals("onOK")) {
	                                redirect(uri:'/medicine/prescription.zul')
	                            }
	                        }
	                    }
	                )
			})

			$("#btnCancle").on("click", {
				redirect(uri:'/medicine/prescription.zul')
			})
		}
    }
}
