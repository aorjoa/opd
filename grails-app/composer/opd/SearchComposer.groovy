package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class SearchComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here
       // println param
        if (!session['user']){
			redirect(uri:'/registers/login.zul')
		}else if (session.user.user.equals('admin')) {
			redirect(uri:'/registers/searchNurse.zul')
		} else {
			window.visible = true

			$("#logout").on("click", {
				session.user = null
				redirect(uri:'/index.zul')
			})

			$("#logonName").val(session.user.user)
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
    	def count = 1
    	def editPatientInfoBtn
    	def saveSymptomBtn

    	//<label id="tel"/>

    	//$("#tel").val(pateint.tel)

		$("grid rows").detach()
		$("grid").append { rows()}
		data.each { patient ->
			$("grid rows").append {
				row() {
					label( value: "${count}")
					label( value: "${patient.firstname}")
					label( value: "${patient.lastname}")
					hbox () {
						button(label:'Edit')
						button(label:'Save')
					}
				}
			}
			editPatientInfoBtn =  $("grid rows row:last-child button[label='Edit']")
			editPatientInfoBtn.on("click",{
				session.editPatient = patient
				redirect(uri:'/registers/editPatientInfo.zul')
			})


			saveSymptomBtn =  $("grid rows row:last-child button[label='Save']")
			saveSymptomBtn.on("click",{
				session.savePatient = patient
				redirect(uri:'/registers/saveSymptom.zul')
			})

		count++
		}
    }
}
