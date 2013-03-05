package opd

import org.zkoss.zk.grails.composer.*

import org.zkoss.zk.ui.select.annotation.Wire
import org.zkoss.zk.ui.select.annotation.Listen

class TransferInComposer extends GrailsComposer {
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
			$("#comment").val(tin.transfer.comment[0])
			dname = Patient.findByIdcard($("#idcard").val())
			$("#dtransfer").val(dname.symptoms.doctor.firstname[0]+ " " + dname.symptoms.doctor.lastname[0])
			$("#diagnose").val(dname.symptoms.diagnose[0])
		}
		$("#searchbtn").on("click", {
				redirect(uri:'/transfer/transfersearch.zul')
			})
		$("#transferbtn").on("click", {
				redirect(uri:'/transfer/transferOut.zul')
			})

		$("#viewbtn").on("click", {
				redirect(uri:'/transfer/previewtransferslip.zul')
			})
		$("#savebtn").on("click", {
			if($("#type1").isSelected()){
				alert("ส่ง -> "+$("#type1").text()+"\nบันทึกสำเร็จ..^^")
				test = Transfer.findByPatient(dname)
				test.type = "Lab"
				test.date = new Date()
				test.save()
			}else{$("#type2").isSelected()
				alert("ส่ง -> "+$("#type2").text()+"\nบันทึกสำเร็จ..^^")
				test = Transfer.findByPatient(dname)
				test.type = "X-Ray"
				test.date = new Date()
				test.save()
			}
            	
			})
		
	
		
    }
}
