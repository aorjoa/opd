package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen 



class PrescriptionComposer extends GrailsComposer {

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

			def patient = session.patient
			def phar = session.user

			$("#lbName").val(patient.firstname + " " + patient.lastname)
			
			
			def prescriptionInfo = Prescription.findByPatient(patient)
			prescriptionInfo.pharmacist = phar
			def p = prescriptionInfo.comment
			def pa = p.split("\\|")
			addToGrid(pa)


			$("#btnHistory").on("click", {
				session.prescription = patient
				redirect(uri:'/medicine/history.zul')
			})
			$("#btnBack").on("click", {
				session.patient=null
				redirect(uri:'/medicine/searchPrescription.zul')
			})

			$("#btnSave").on("click", {
				prescriptionInfo.save()
				redirect(uri:'/medicine/price.zul')
			})

			session.patient = patient

		}
    }
    def addToGrid(data) {
    	def btnSticker

		$("grid rows").detach()
		$("grid").append { rows()}
		data.each { pre ->

			def nameAndValue = pre.split("/")
			def name = nameAndValue[0]
			def medicinevalue = nameAndValue[1]
			def medicineInfo = MedicineInfo.findByName(name)
			def type = medicineInfo.type

			$("grid rows").append {
				row() {
					label( value: "${name}")
					label( value: "${type}")
					label( value: "${medicinevalue}")
					button(label:'พิมพ์')
				}
			}
			btnSticker = $("grid rows row:last-child button[label='พิมพ์']")

			btnSticker.on("click",{
				session.prescriptionInfo = pre
				redirect(uri:'/medicine/print.zul')
			})
		}

    }
}
