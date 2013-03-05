package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class LoginRegisterComposer extends GrailsComposer {

    def afterCompose = { window ->

        // initialize components here
        if (session['user'])
      redirect(uri:'/registers/searchPatient.zul')
    else
      window.visible = true

        $("#login").on("click", {

          def user = Nurse.findByUserAndPassword($("#username").val(), $("#password").val())
          if (user) {
            session.user = user
            redirect(uri:'/registers/searchPatient.zul')
          }
        })
    }
}

