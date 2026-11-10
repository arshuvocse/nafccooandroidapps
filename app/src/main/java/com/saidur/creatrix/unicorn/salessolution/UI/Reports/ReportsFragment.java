package com.saidur.creatrix.unicorn.salessolution.UI.Reports;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.saidur.creatrix.unicorn.salessolution.Adapters.Adapter_ReportList;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelReportsMenu;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Dar_N_coaching.ActivityDar;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.ActivityDCPApproval;   // Call Plan
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.ActivityDCRApproval;   // Call Report
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.ActivityTwwpApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Attendance.ActivityRpt_Attendance;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Customer.ActivityCustCall;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Customer.ActivityCustomerDueList;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Doctor.ActivityDoctorList;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Product.ReportProducts;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Rx.ReportRxActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.ReportSalesActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Stock.ReportStock;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentReportsBinding;
import com.saidur.creatrix.unicorn.salessolution.webview.DashboardOverviewWeb;
import com.saidur.creatrix.unicorn.salessolution.webview.RxOverviewWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportsFragment extends Fragment {

    FragmentReportsBinding binding;
    DBCrudHelper dbCrudHelper;
    ModelSession session;
    String RoleType = "";

    public ReportsFragment() {}

    public static ReportsFragment newInstance(String p1, String p2) {
        ReportsFragment fragment = new ReportsFragment();
        Bundle args = new Bundle();
        args.putString("param1", p1);
        args.putString("param2", p2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportsBinding.inflate(getLayoutInflater());
        dbCrudHelper = new DBCrudHelper(requireActivity());
        session = dbCrudHelper.getSession();

        // Normalize role to avoid case/space mismatch
        RoleType = session != null && session.getEmpRole() != null
                ? session.getEmpRole().trim().toUpperCase(Locale.US)
                : "";

        initMenus();
        return binding.getRoot();
    }

    private void initMenus() {
        final List<ModelReportsMenu> rmListMBE = getMenu1();
        final List<ModelReportsMenu> rmListMioAm = getMenu2();

        switch (RoleType) {
            case "MBE": {
                Adapter_ReportList adapter = new Adapter_ReportList(requireContext(), rmListMBE, "ALL");
                binding.gridPropertyMenu.setAdapter(adapter);

                binding.gridPropertyMenu.setOnItemClickListener((adapterView, view, i, l) -> {
                    int id = rmListMBE.get(i).getId();
                    switch (id) {
                        case 1:
                            startActivity(new Intent(requireActivity(), ActivityRpt_Attendance.class));
                            break;
                        case 2: {
                            Intent it = new Intent(requireActivity(), ReportSalesActivity.class);
                            it.putExtra("From", "Order");
                            startActivity(it);
                            break;
                        }
                        case 3: {
                            Intent it = new Intent(requireActivity(), ReportSalesActivity.class);
                            it.putExtra("From", "Sales");
                            startActivity(it);
                            break;
                        }
                        case 4:
                            startActivity(new Intent(requireActivity(), ReportRxActivity.class));
                            break;
                        case 5:
                            startActivity(new Intent(requireActivity(), ReportProducts.class));
                            break;
                        case 6: {
                            Intent it = new Intent(requireActivity(), ReportStock.class);
                            it.putExtra("menu", "ProductStock");
                            startActivity(it);
                            break;
                        }
                        case 7: {
                            Intent it = new Intent(requireActivity(), ReportStock.class);
                            it.putExtra("menu", "PromoStock");
                            startActivity(it);
                            break;
                        }
                        case 8: {
                            Intent it = new Intent(requireActivity(), ActivityDoctorList.class);
                            it.putExtra("Who", "Report");
                            startActivity(it);
                            break;
                        }
                        case 9: // Chemist Call
                            startActivity(new Intent(requireActivity(), ActivityCustCall.class));
                            break;
                        case 15:
                            startActivity(new Intent(requireActivity(), ActivityCustomerDueList.class));
                            break;
                        case 10: // Call Plan → DCP
                        {
                            Intent it = new Intent(requireActivity(), ActivityDCPApproval.class);
                            it.putExtra("Who", "Report");
                            startActivity(it);
                            break;
                        }
                        case 11: // Call Report → DCR
                            startActivity(new Intent(requireActivity(), ActivityDCRApproval.class));
                            break;
                        case 16: // RX Reports
                            startActivity(new Intent(requireActivity(), RxOverviewWeb.class));
                            break;
                        case 17: // Dashboard Overview
                            startActivity(new Intent(requireActivity(), DashboardOverviewWeb.class));
                            break;
                        default:
                            Toast.makeText(requireActivity(), "Not mapped!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
                break;
            }
            case "MIO":
            case "AM":
            case "DZSM":
            case "NSM": {
                Adapter_ReportList adapter = new Adapter_ReportList(requireContext(), rmListMioAm, "ALL");
                binding.gridPropertyMenu.setAdapter(adapter);

                binding.gridPropertyMenu.setOnItemClickListener((adapterView, view, i, l) -> {
                    int id = rmListMioAm.get(i).getId();
                    switch (id) {
                        case 1:
                            startActivity(new Intent(requireActivity(), ActivityRpt_Attendance.class));
                            break;
                        case 2: {
                            Intent it = new Intent(requireActivity(), ReportSalesActivity.class);
                            it.putExtra("From", "Order");
                            startActivity(it);
                            break;
                        }
                        case 3: {
                            Intent it = new Intent(requireActivity(), ReportSalesActivity.class);
                            it.putExtra("From", "Sales");
                            startActivity(it);
                            break;
                        }
                        case 4:
                            startActivity(new Intent(requireActivity(), ReportRxActivity.class));
                            break;
                        case 5:
                            startActivity(new Intent(requireActivity(), ReportProducts.class));
                            break;
                        case 6: {
                            Intent it = new Intent(requireActivity(), ReportStock.class);
                            it.putExtra("menu", "ProductStock");
                            startActivity(it);
                            break;
                        }
                        case 7: {
                            Intent it = new Intent(requireActivity(), ReportStock.class);
                            it.putExtra("menu", "PromoStock");
                            startActivity(it);
                            break;
                        }
                        case 8: {
                            Intent it = new Intent(requireActivity(), ActivityDoctorList.class);
                            it.putExtra("Who", "Report");
                            startActivity(it);
                            break;
                        }
                        case 9: // Chemist Call
                            startActivity(new Intent(requireActivity(), ActivityCustCall.class));
                            break;
                        case 10: // Call Plan → DCP
                            startActivity(new Intent(requireActivity(), ActivityDCPApproval.class));
                            break;
                        case 11: // Call Report → DCR
                            startActivity(new Intent(requireActivity(), ActivityDCRApproval.class));
                            break;
                        case 15:
                            startActivity(new Intent(requireActivity(), ActivityCustomerDueList.class));
                            break;
                        case 14: // DAR
                            startActivity(new Intent(requireActivity(), ActivityDar.class));
                            break;
                        case 16: // RX Reports
                            startActivity(new Intent(requireActivity(), RxOverviewWeb.class));
                            break;
                        case 17: // Dashboard Overview
                            startActivity(new Intent(requireActivity(), DashboardOverviewWeb.class));
                            break;
                        default:
                            Toast.makeText(requireActivity(), "Not mapped!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
                break;
            }
            default: {
                Adapter_ReportList adapter = new Adapter_ReportList(requireContext(), rmListMBE, "ALL");
                binding.gridPropertyMenu.setAdapter(adapter);

                binding.gridPropertyMenu.setOnItemClickListener((adapterView, view, i, l) -> {
                    int id = rmListMBE.get(i).getId();
                    switch (id) {
                        case 1:
                            startActivity(new Intent(requireActivity(), ActivityRpt_Attendance.class));
                            break;
                        case 2: {
                            Intent it = new Intent(requireActivity(), ReportSalesActivity.class);
                            it.putExtra("From", "Order");
                            startActivity(it);
                            break;
                        }
                        case 3: {
                            Intent it = new Intent(requireActivity(), ReportSalesActivity.class);
                            it.putExtra("From", "Sales");
                            startActivity(it);
                            break;
                        }
                        case 4:
                            startActivity(new Intent(requireActivity(), ReportRxActivity.class));
                            break;
                        case 5:
                            startActivity(new Intent(requireActivity(), ReportProducts.class));
                            break;
                        case 6: {
                            Intent it = new Intent(requireActivity(), ReportStock.class);
                            it.putExtra("menu", "ProductStock");
                            startActivity(it);
                            break;
                        }
                        case 7: {
                            Intent it = new Intent(requireActivity(), ReportStock.class);
                            it.putExtra("menu", "PromoStock");
                            startActivity(it);
                            break;
                        }
                        case 8: {
                            Intent it = new Intent(requireActivity(), ActivityDoctorList.class);
                            it.putExtra("Who", "Report");
                            startActivity(it);
                            break;
                        }
                        case 9: // Chemist Call
                            startActivity(new Intent(requireActivity(), ActivityCustCall.class));
                            break;
                        case 15:
                            startActivity(new Intent(requireActivity(), ActivityCustomerDueList.class));
                            break;
                        case 10: // Call Plan → DCP
                        {
                            Intent it = new Intent(requireActivity(), ActivityDCPApproval.class);
                            it.putExtra("Who", "Report");
                            startActivity(it);
                            break;
                        }
                        case 11: // Call Report → DCR
                            startActivity(new Intent(requireActivity(), ActivityDCRApproval.class));
                            break;
                        case 16: // RX Reports
                            startActivity(new Intent(requireActivity(), RxOverviewWeb.class));
                            break;
                        case 17: // Dashboard Overview
                            startActivity(new Intent(requireActivity(), DashboardOverviewWeb.class));
                            break;
                        default:
                            Toast.makeText(requireActivity(), "Not mapped!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
                break;
            }
        }
    }

    private List<ModelReportsMenu> getMenu1() {
        List<ModelReportsMenu> rmList = new ArrayList<>();
        rmList.add(new ModelReportsMenu(1,  R.drawable.ic_attendance,     "Attendance"));
        rmList.add(new ModelReportsMenu(2,  R.drawable.ic_shop,           "Order Summary"));
        rmList.add(new ModelReportsMenu(3,  R.drawable.ic_order,          "Sales Summary"));
        rmList.add(new ModelReportsMenu(4,  R.drawable.ic_prescriptionz,  "Rx."));
        rmList.add(new ModelReportsMenu(5,  R.drawable.ic_box,            "Product"));
        rmList.add(new ModelReportsMenu(6,  R.drawable.ic_box,            "Saleable Stock"));
        rmList.add(new ModelReportsMenu(7,  R.drawable.ic_box,            "Promo Stock"));
        rmList.add(new ModelReportsMenu(8,  R.drawable.ic_doctor,         "Doctor"));
        rmList.add(new ModelReportsMenu(9,  R.drawable.ic_announcement,   "Chemist Call"));
        rmList.add(new ModelReportsMenu(15, R.drawable.ic_money,          getString(R.string.customer_due_menu)));
        rmList.add(new ModelReportsMenu(10, R.drawable.ic_visitss,        "Call Plan"));   // DCP
        rmList.add(new ModelReportsMenu(11, R.drawable.ic_rep,            "Call Report")); // DCR
        rmList.add(new ModelReportsMenu(16, R.drawable.ic_prescriptions,  "RX Reports"));
        rmList.add(new ModelReportsMenu(17, R.drawable.ic_board,          "Dashboard Overview"));
        return rmList;
    }

    private List<ModelReportsMenu> getMenu2() {
        List<ModelReportsMenu> rmList = new ArrayList<>();
        rmList.add(new ModelReportsMenu(1,  R.drawable.ic_attendance,     "Attendance"));
        rmList.add(new ModelReportsMenu(2,  R.drawable.ic_shop,           "Order Summary"));
        rmList.add(new ModelReportsMenu(3,  R.drawable.ic_order,          "Sales Summary"));
        rmList.add(new ModelReportsMenu(4,  R.drawable.ic_prescriptionz,  "Rx."));
        rmList.add(new ModelReportsMenu(5,  R.drawable.ic_box,            "Product"));
        rmList.add(new ModelReportsMenu(6,  R.drawable.ic_box,            "Saleable Stock"));
        rmList.add(new ModelReportsMenu(7,  R.drawable.ic_box,            "Promo Stock"));
        rmList.add(new ModelReportsMenu(8,  R.drawable.ic_doctor,         "Doctor"));
        rmList.add(new ModelReportsMenu(9,  R.drawable.ic_announcement,   "Chemist Call"));
        rmList.add(new ModelReportsMenu(15, R.drawable.ic_money,          getString(R.string.customer_due_menu)));
        rmList.add(new ModelReportsMenu(10, R.drawable.ic_visitss,        "Call Plan"));   // DCP
        rmList.add(new ModelReportsMenu(11, R.drawable.ic_rep,            "Call Report")); // DCR
        // WS View removed
        rmList.add(new ModelReportsMenu(14, R.drawable.ic_rep,            "DAR"));
        rmList.add(new ModelReportsMenu(16, R.drawable.ic_prescriptions,  "RX Reports"));
        rmList.add(new ModelReportsMenu(17, R.drawable.ic_board,          "Dashboard Overview"));
        return rmList;
    }
}
