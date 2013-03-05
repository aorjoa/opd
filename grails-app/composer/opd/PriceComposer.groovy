package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.Messagebox

class PriceComposer extends GrailsComposer {

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

			$("#btnBack").on("click", {

				redirect(uri:'/medicine/prescription.zul')
			})

			def patient = session.patient

			$("#lbName").val(patient.firstname + " " + patient.lastname)

			def prescriptionInfo = Prescription.findByPatient(patient)
			def p = prescriptionInfo.comment
			def pa = p.split("\\|")
			addToGrid(pa)			
			
		}
    }

    def addToGrid(data) {
    	def btnSticker
    	def priceperunit
    	def patient = session.patient
    	def sum = 0
    	
    	def pInfo = Prescription.findByPatient(patient)

		$("grid rows").detach()
		$("grid").append { rows()}
		data.each { pre ->

			def nameAndValue = pre.split("/")
			def name = nameAndValue[0]
			def medicinevalue = nameAndValue[1]
			def medicineInfo = MedicineInfo.findByName(name)
			def ppu = medicineInfo.priceperunit
			def ppuI = 0
			ppuI = ppuI + (ppu * medicinevalue.toInteger())

			$("grid rows").append {
				row() {
					label( value: "${name}")
					label( value: "${ppu}")
					label( value: "${medicinevalue}")
					label( value: "${ppuI}")
				}
			}
			
			sum = sum+ppuI
			pInfo.total = sum

		}
		$("#btnSum").on("click", {
			$("#txtPrice").val(sum)
			pInfo.save()
		})
		$("#btnSavePrice").on("click", {

				Messagebox.show("บันทึกสำเร็จสำเร็จ", null, Messagebox.OK, Messagebox.INFORMATION,
	                    new org.zkoss.zk.ui.event.EventListener() {
	                        public void onEvent(Event evt) throws InterruptedException {
	                            if (evt.getName().equals("onOK")) {
	                                redirect(uri:'/medicine/searchPrescription.zul')
	                            }
	                        }
	                    }
	            )
			})

    }
}
