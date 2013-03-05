package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class MedicineLoginComposer extends GrailsComposer {

    def afterCompose = { window ->
        if (session['user'])
			redirect(uri:'/medicine/searchPrescription.zul')
		else
			window.visible = true

		$("#login").on("click", {

        	def user = Pharmacist.findByUserAndPassword($("#txtUser").val(), $("#txtPass").val())
        	if (user) {
        		session.user = user
        		redirect(uri:'/medicine/searchPrescription.zul')
        	}else{
        		alert("เกิดข้อผิดพลาด กรุณาติดต่อเจ้าหน้าที่")
        		$("#txtUser").setValue(null)
        		$("#txtPass").setValue(null)
        	}
        })
    }
}
