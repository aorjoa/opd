package opd

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class PrintVisitSlipComposer extends GrailsComposer {

    def afterCompose = { window ->
        // initialize components here
        if (!session['user'])
			redirect(uri:'/login.zul')
		else {
			window.visible = true

			def patient = session.savePatient
			def symptom = session.symptom

			$("#name").val(patient.firstname+" "+patient.lastname)
			
			$("#tel").val(patient.tel)

			$("#idcard").val(patient.idcard)

			def now = new Date()
			def age = now[Calendar.YEAR] - patient.birthday[Calendar.YEAR]
			$("#age").val(age)

			$("#birthday").val(patient.birthday)

			$("#time").val(now)

			$("#status").val(patient.welfare)

			$("#allergic").val(patient.allergic)

			$("#symptom").val(symptom.description)


	        $("#cancel").on("click",{
	        	redirect(uri:'/registers/searchPatient.zul')
	        })
	    }
    }
}