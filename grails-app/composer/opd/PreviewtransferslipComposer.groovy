package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class PreviewtransferslipComposer extends GrailsComposer {
	def dname
    def tin
    def	test
    def afterCompose = { window ->
        // initialize components here
         tin = session.patient
			$("#name").val(tin.firstname + " " +tin.lastname)
			$("#idcard").val(tin.idcard)
			$("#tel").val(tin.tel)
			
			$("#comment").val(tin.transfer.comment[0])
		 dname = Patient.findByIdcard($("#idcard").val())
			$("#dtransfer").val(dname.symptoms.doctor.firstname[0]+ " " + dname.symptoms.doctor.lastname[0])
			$("#diagnose").val(dname.symptoms.diagnose[0])
		test = Transfer.findByPatient(dname)
			$("#type").val(test.type)
			$("#timetransfer").val(test.date)

        $("#printbtn").on("click", {
				redirect(uri:'/transfer/printtransferslip.zul')
			})
        $("#backbtn").on("click", {
				redirect(uri:'/transfer/transferIn.zul')
			})
    }
}
