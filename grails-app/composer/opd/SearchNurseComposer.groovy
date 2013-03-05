package opd

import org.zkoss.zk.grails.composer.*
import org.zkoss.zul.Messagebox
import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class SearchNurseComposer extends GrailsComposer {

    def afterCompose = { window ->
        // check login
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

			// load Nurse
			def allNurse = Nurse.list()
			addToGrid(allNurse)

			// search
			$("#btnSearch").on("click", {
				def searchText = $("#searchText").val()

				def results = Nurse.findAllByFirstnameLikeOrLastnameLike("%${searchText}%","%${searchText}%")

				addToGrid(results)
				
			})
		}
		
    }

    def addToGrid(data) {
    	def count = 1
    	def editBtn
    	def deleteBtn

		$("grid rows").detach()
		$("grid").append { rows()}
		data.each { nurse ->
			$("grid rows").append {
				row() {
					label( value: "${count}")
					label( value: "${nurse.user}")
					label( value: "${nurse.firstname}")
					label( value: "${nurse.lastname}")
					hbox () {
						button(label:'Edit')
						button(label:'Delete')
					}
				}
			}
			editBtn =  $("grid rows row:last-child button[label='Edit']")
			editBtn.on("click",{
				session.editNurse = nurse
				redirect(uri:'/registers/editNurse.zul')
			})
			if (!nurse.user.equals("admin")) {
				deleteBtn = $("grid rows row:last-child button[label='Delete']")
				deleteBtn.on("click",{
					Messagebox.show("Are you sure?", "Delete", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
						    public void onEvent(org.zkoss.zk.ui.event.Event evt) throws InterruptedException {
						        if (evt.getName().equals("onOK")) {
						        	// when click confirm ok
						        	// remove nurse from database
						        	nurse.delete()
						        	// remove row from grid
						            it.target.parent.parent.detach()
						        }
						    }
						}
					)
				})	
			} else
			$("grid rows row:last-child button[label='Delete']").detach()
			
			count++
		}
    }

}
