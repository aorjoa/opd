package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class TransfersearchComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here
        if (!session['user'])
			redirect(uri:'/transfer/logintransfer.zul')
		else {
			window.visible = true

			$("#logout").on("click", {
				session.user = null
				redirect(uri:'/index.zul')
			})

			$("#logonName").val(session.user.firstname)
			$("#today").val(new Date())

			// load Patient
			def allPatient = Patient.list()
			addToGrid(allPatient)

			// search
			$("#btnSearch").on("click", {
				def searchText = $("#searchText").val()

				def results = Patient.findAllByFirstnameLikeOrLastnameLike("%${searchText}%","%${searchText}%")

				addToGrid(results)
				
			})
		}
			
    }

    def addToGrid(data) {
    	def selectbtn

		$("grid rows").detach()
		$("grid").append { rows()}
		data.each { patient ->
			$("grid rows").append {
				row() {
					label( value: "${patient.firstname}" + " " + "${patient.lastname}")
					label( value: "${patient.transfer.comment[0]}")
					button(label:'เลือก')
					
				}
				
			}
			selectbtn =  $("grid rows row:last-child button[label='เลือก']")
				selectbtn.on("click",{
					session.patient = patient
				redirect(uri:'/transfer/transferIn.zul')
			})

		}

		}
    }

