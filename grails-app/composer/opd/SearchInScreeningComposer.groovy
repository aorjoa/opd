package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class SearchInScreeningComposer extends GrailsComposer {

    def afterCompose = { window ->
     if (!session['user'])
			redirect(uri:'/screening/login2.zul')

			else {
					window.visible = true
					$("#logout").on("click", {
						session.user = null
						redirect(uri:'/index.zul')
					})
		    	def allPatient = Patient.list()
		    	
			//		addToGrid(allPatient)
		    	$("#btnSearch").on("click", {
		    		if ($("#txtname").val()!=""){
		    			def searchText = $("#txtname").val()
						def results = Patient.findAllByFirstnameLikeOrLastnameLike("%${searchText}%","%${searchText}%")
						addToGrid(results)
		    		}else{
		    			
		    			$("grid rows").detach()
		    		}
						
				})

			}

    }

    def addToGrid(data) {
    	//def count = 1
    	def selectPatientInfoBtn
    	//def saveSymptomBtn

		$("grid rows").detach()
		$("grid").append { rows()}
		data.each { patient ->
			$("grid rows").append {
				row() {
					label( value: "${patient.idcard}")
					label( value: "${patient.firstname}")
					label( value: "${patient.lastname}")
					label( value: "${patient.sex}")
					//hbox () {
						button(label:'Select')
						//button(label:'Save')
					//}
				}
			}
			selectPatientInfoBtn =  $("grid rows row:last-child button[label='Select']")
			selectPatientInfoBtn.on("click",{
				session.editPatient = patient
				redirect(uri:'/screening/screening.zul')
			})



		//count++
		}
    }
}
