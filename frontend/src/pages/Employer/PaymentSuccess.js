import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function PaymentSuccess() {

  const location = useLocation();

  useEffect(() => {
  console.log("🔥 PAYMENT PAGE LOADED");

  const params = new URLSearchParams(window.location.search);
  const orderId = params.get("orderId");

  console.log("🔥 ORDER ID:", orderId);

  if (!orderId) return;

  API.post("/momo/confirm", { orderId })
    .then(res => {
      console.log("✅ CONFIRM SUCCESS", res.data);
    })
    .catch(err => {
      console.error("❌ CONFIRM ERROR", err);
    });

}, []);
return (
  <EmployerLayout>
    <div style={{ padding: "40px" }}>
      <h1>Payment Success</h1>
    </div>
  </EmployerLayout>
);
  
}