package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class HistoryComposer extends GrailsComposer {

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

			$("#btnClose").on("click", {
				redirect(uri:'/medicine/prescription.zul')
			})

			def patient = session.prescription

			$("#lbName").val(patient.firstname + " " + patient.lastname)

			def prescriptionInfo = Prescription.findByPatient(patient)
			addToGrid(prescriptionInfo)
		}
    }

    def addToGrid(data) {

		$("grid rows").detach()
		$("grid").append { rows()}
		data.each { history ->
			def hiss = history.id
			def his = history.comment
			$("grid rows").append {
				row() {
					label( value: "${hiss}")
					label( value: "${his}")
				}
			}
		}

    }
}
