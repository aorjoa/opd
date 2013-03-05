package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class LoginComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here

        if (session['user'])
			redirect(uri:'/home.zul')
		else
			window.visible = true
		
		$("#login").on("click", {

        	def user = Account.findByUseridAndPassword($("#user").val(), $("#pass").val())
        	if (user) {
        		session.user = user
        		redirect(uri:'/home.zul')
        	}else {
				$("#user").val(null)
				$("#pass").val(null)
			}
        })
		
		$("#clear").on("click", {

				$("#user").val(null)
				$("#pass").val(null)
			
        })
    }
}
