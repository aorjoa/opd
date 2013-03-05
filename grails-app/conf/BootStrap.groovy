import opd.*
class BootStrap {

    def init = { servletContext ->
      def nurse
        def birthday
        def patient
        def patient2
        def symptom
        def doctor
        def pharmacist
        def prescription
        def medicineInfo
        def transfer
        def account

      nurse = new Nurse(firstname:"pawee", lastname:"chongfsf" , user:"admin" , password:"admin")
       if (nurse.validate()) {
          nurse.save()
      }else println nurse.errors

      nurse = new Nurse(firstname:"Kanokon", lastname:"Chansom", user:"ihippo", password:"12345")
      if (nurse.validate()) {
          nurse.save()
      }else println nurse.errors

      birthday = new GregorianCalendar(1999, Calendar.DECEMBER, 25)

      patient = new Patient(firstname:"Patient1",
          lastname:"LPatient1",
          idcard:"B123456",
          sex:"Male",
          birthday: birthday.time,
          tel: "022222222",
          race: "Thai",
          nationality: "Thai",
          religion: "Thai",
          work: "Student",
          welfare: "นักศึกษา", //สิทธิ์การรักษา
          allergic: "แพ้ยา" //อาการแพ้ยา
      )

      if (patient.validate()) {
          patient.save()
      }else println patient.errors

      patient2 = new Patient(firstname:"Patient2",
          lastname:"LPatient2",
          idcard:"B123456",
          sex:"Male",
          birthday: birthday.time,
          tel: "022222222",
          race: "Thai",
          nationality: "Thai",
          religion: "duhha",
          work: "Student",
          welfare: "นักศึกษา", //สิทธิ์การรักษา
          allergic: "แพ้ยา" //อาการแพ้ยา
          )
      
      if (patient2.validate()) {
          patient2.save()
      }else println patient.errors
      nurse = Nurse.get(1)
      patient = Patient.get(1)
      patient2 = Patient.get(2)

      symptom = new Symptom(description:"H5N1", nurse: nurse, patient: patient, date:new Date())

      if (symptom.validate())
          symptom.save()
      else println symptom.errors
          
      symptom = Symptom.get(1)

      symptom = new Symptom(description:"H1N1", nurse: nurse, patient: patient, date: new Date())

      if (symptom.validate())
          symptom.save()
      else println symptom.errors
         
      symptom = Symptom.get(2)
      patient = Patient.get(1)

      doctor = new Doctor(firstname:"ภูริเดช",lastname:"สุดสี",username:"user1",password:"1234",iddoctor:"001",status:"Porf.",section:"อายุรกรรม",room:"1",tel:"0989876644")
      if(doctor.validate()){
        doctor.save()
      }

      doctor = new Doctor(firstname:"อนุชิต",lastname:"ประเสริฐสังข์",username:"user2",password:"4567",iddoctor:"002",status:"Porf.",section:"ทันตกรรม",room:"2",tel:"0898765676")
      if(doctor.validate()){
        doctor.save()
      }

      doctor = Doctor.get(1)
      symptom.diagnose = "ปวดท้องเป็นโรคกระเพาะอักเสบ"
      symptom.doctor = doctor
      symptom.merge()
 medicineInfo = new MedicineInfo(name:"Paracetamol",type:"Pill",priceperunit: 1)
      if(medicineInfo.validate()){
        medicineInfo.save()
      }
      medicineInfo = MedicineInfo.get(1)

          pharmacist = new Pharmacist(firstname:"Thirawuth",lastname:"Chaicha_um",user:"champ",password:"1234")
      if(pharmacist.validate()){
        pharmacist.save()
      }else println pharmacist.errors
      pharmacist = Pharmacist.get(1)

      prescription = new Prescription(comment:"Paracetamol/20|Aspirin/10", doctor:doctor, patient:patient)
      if(prescription.validate()){
        prescription.save()
      }
      def prescription1 = new Prescription(comment:"ยาแก้ไอชนิดน้ำ/2|Moxilin/20", doctor:doctor, patient:patient2)
      if(prescription1.validate()){
        prescription1.save()
      }

     // prescription.pharmacist = pharmacist
      prescription.total = 20
      prescription.merge()

        transfer = new Transfer(detial: "รักษาไข้",comment: "เช็คผลเลือด",type: "X-ray",date: new Date(),nurse: nurse,doctor: doctor,patient: patient)
        if(transfer.validate()){
          transfer.save()
        } else println transfer.errors


        account = new Account(userid:"kop",password:"1234",fname:"Apichat",lname:"Eakwongsa",address:"22 หมู่ 4 ตำบลพระธาตุ อำเภอเชียงขวัญ จังหวัดร้อยเอ็ด",phone:"0800103329")
        if(account.validate()){
          account.save()
        } else println account.errors
        account = new Account(userid:"admin",password:"admin",fname:"Ia m k",lname:"Loop",address:"Lenovo",phone:"0808076543")
        if(account.validate()){
          account.save()
        } else println account.errors

        def med = new MedicineInfo(name:"Aspirin",type:"Pill",priceperunit: 1)
        med.save()
        def med2 = new MedicineInfo(name:"Nasolin",type:"Pill",priceperunit: 1)
        med2.save()
        def med3 = new MedicineInfo(name:"Moxilin",type:"Capsules",priceperunit: 1)
        med3.save()
        def med4 = new MedicineInfo(name:"ยาแก้ไอชนิดน้ำ",type:"liquid",priceperunit: 10)
        med4.save()
        def med5 = new MedicineInfo(name:"ยาแก้ไอชนิดเม็ด",type:"Pill",priceperunit: 1)
        med5.save()
        def med6 = new MedicineInfo(name:"Counterpain",type:"Cream",priceperunit: 15)
        med6.save()
    }
    def destroy = {
    }
}
