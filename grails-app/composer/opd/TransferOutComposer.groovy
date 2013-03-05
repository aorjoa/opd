package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class TransferOutComposer extends GrailsComposer {
	def dname
	def test
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

			def tin = session.patient
			$("#firstname").val(tin.firstname)
			$("#lastname").val(tin.lastname)
			$("#idcard").val(tin.idcard)
			$("#tel").val(tin.tel)
			dname = Patient.findByIdcard($("#idcard").val())
			$("#dtransfer").val(dname.symptoms.doctor.firstname[0]+ " " + dname.symptoms.doctor.lastname[0])
			$("#diagnose").val(dname.symptoms.diagnose[0])


			


			def nursein = session.user
			$("#nursetran").val(nursein.firstname + " " + nursein.lastname)
			
		}
		$("#searchbtn").on("click", {
				redirect(uri:'/transfer/transfersearch.zul')
			})
		$("#backbtn").on("click", {
				redirect(uri:'/transfer/transferIn.zul')
			})
		$("#viewbtn").on("click", {
				redirect(uri:'/transfer/previewtransferslip2.zul')
			})
		$("#savebtn").on("click", {
				
			def x = $("#location").getSelectedItem().getLabel()
			alert(x + "\nเลือกโรงพยาบาลสำเร็จ")
			test = Transfer.findByPatient(dname)
				test.location = x
				test.date = new Date()
				test.save()

			
			})
		
    }
}
