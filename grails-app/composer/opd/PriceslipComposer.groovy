package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class PriceslipComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here
        if (!session['user'])
			redirect(uri:'/login.zul')
		else{
			window.visible = true	
		}
		def patient = Patient.findByIdcard(session.namepp)
		$('#name').val(patient.firstname+" "+patient.lastname)
		$('#tel').val(session.user.phone)
		$('#symptom').val(patient.symptoms.diagnose[0])
		$('#date').val(new Date())	
		$('#Ya').val(patient.prescription.total[0]+"บาท")
		if(session.mlab==null) {$('#lab').val(" 0 บาท")} else {$('#lab').val(session.mlab+" บาท")}
		if(session.mxray==null) {$('#Xray').val(" 0 บาท")} else {$('#Xray').val(session.mxray+" บาท")}
		$('#pat').val("200 บาท")
		$('#sum').val(patient.price.sumprice[0]+ "บาท")

		$('#btnBack').on("click", {
			//sum = null
			redirect(uri:'/finance/pricehome.zul')
		})
    }
}
