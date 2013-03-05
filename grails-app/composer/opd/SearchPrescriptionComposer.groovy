package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class SearchPrescriptionComposer extends GrailsComposer {

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

			

			$("#btnSearch").on("click", {
				def search = $("#txtSearch").val()

				def res = Patient.findAllByFirstnameLikeOrLastnameLike("%${search}%","%${search}%")
				addToGrid(res)
				
			})
		}
			
    }

    def addToGrid(data) {
    	def btnInfo

		$("grid rows").detach()
		$("grid").append { rows()}
		data.each { patient ->

			$("grid rows").append {
				row() {
					label( value: "${patient.prescription.id[0]}")
					label( value: "${patient.firstname}" + "   " + "${patient.lastname}")
					label( value: "${patient.prescription.comment[0]}")
					button(label:'ดูรายละเอียด')
				}
			}
			btnInfo = $("grid rows row:last-child button[label='ดูรายละเอียด']")

			btnInfo.on("click",{
				session.patient = patient
				redirect(uri:'/medicine/prescription.zul')
			})
		}
    }
}
