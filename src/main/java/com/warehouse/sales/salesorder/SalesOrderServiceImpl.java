package com.warehouse.sales.salesorder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketRepository;
import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.freighter.FreighterRepository;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.ordertype.OrderTypeRepository;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.uom.UomRepository;
import com.warehouse.additionalsetup.vasactivity.VasActivity;
import com.warehouse.additionalsetup.vasactivity.VasActivityRepository;
import com.warehouse.country.Country;
import com.warehouse.country.CountryRepository;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.sales.salesorder.reasonpendingsalesorders.PendingSalesOrderSearchRepository;
import com.warehouse.sales.salesorder.vehicleinward.SalesOrderCustomResponse;
import com.warehouse.sales.salesorder.vehicleinward.VehicleInward;
import com.warehouse.sales.salesorder.vehicleinward.VehicleInwardRepository;
import com.warehouse.sales.salesorder.vehicleinward.VehicleInwardSearchRepository;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.customer.CustomerRepository;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddress;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddressRepository;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddress;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddressRepository;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.sku.SkuRepository;
import com.warehouse.state.State;
import com.warehouse.state.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SalesOrderServiceImpl implements SalesOrderService{

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SalesOrderSkuRepository salesOrderSkuRepository;

    @Autowired
    private PrimaryCompanyRepository primaryCompanyRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CostBucketRepository costBucketRepository;

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    private CustomerBillToAddressRepository customerBillToAddressRepository;

    @Autowired
    private CustomerShipToAddressRepository customerShipToAddressRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private FreighterRepository freighterRepository;

    @Autowired
    private VasActivityRepository vasActivityRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private UomRepository uomRepository;

    @Autowired
    private SalesOrderSearchRepository salesOrderSearchRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private VehicleInwardRepository vehicleInwardRepository;

    @Autowired
    private VehicleInwardSearchRepository vehicleInwardSearchRepository;

    @Autowired
    private PendingSalesOrderSearchRepository pendingSalesOrderSearchRepository;


    @Override
    public String validateSalesOrderRequest(SalesOrder theSalesOrderRequest) {
        
        PrimaryCompany primaryCompany = primaryCompanyRepository
                        .findById(theSalesOrderRequest.getPrimaryCompanyId())
                        .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                                        theSalesOrderRequest.getPrimaryCompanyId()));
        theSalesOrderRequest.setPrimaryCompany(primaryCompany);

        CostBucket costBucket = costBucketRepository
                        .findById(theSalesOrderRequest.getCostBucketId())
                        .orElseThrow(() -> new ResourceNotFoundException("CostBucket", "id",
                                        theSalesOrderRequest.getCostBucketId()));
        theSalesOrderRequest.setCostBucket(costBucket);


        Customer customer = customerRepository
                        .findById(theSalesOrderRequest.getCustomerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "id",
                                        theSalesOrderRequest.getCustomerId()));
        theSalesOrderRequest.setCustomer(customer);

        CustomerShipToAddress customerShipToAddress = customerShipToAddressRepository
                        .findById(theSalesOrderRequest.getCustomerShipToAddressId())
                        .orElseThrow(() -> new ResourceNotFoundException("CustomerShipToAddress", "id",
                                        theSalesOrderRequest.getCustomerShipToAddressId()));
        theSalesOrderRequest.setCustomerShipToAddress(customerShipToAddress);

        CustomerBillToAddress customerBillToAddress = customerBillToAddressRepository
                        .findById(theSalesOrderRequest.getCustomerBillToAddressId())
                        .orElseThrow(() -> new ResourceNotFoundException("CustomerBillToAddress", "id",
                                        theSalesOrderRequest.getCustomerBillToAddressId()));
        theSalesOrderRequest.setCustomerBillToAddress(customerBillToAddress);

        OrderType orderType = orderTypeRepository
                        .findById(theSalesOrderRequest.getSoTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("SoType", "id",
                                        theSalesOrderRequest.getSoTypeId()));
        theSalesOrderRequest.setSoType(orderType);

        Country country = countryRepository.findById(theSalesOrderRequest.getCountryId()).orElseThrow(
            () -> new ResourceNotFoundException("Country", "id", theSalesOrderRequest.getCountryId()));
        theSalesOrderRequest.setDestinationCountry(country);

        Freighter freighter = freighterRepository.findById(theSalesOrderRequest.getFreighterId()).orElseThrow(
            () -> new ResourceNotFoundException("Freighter", "id", theSalesOrderRequest.getFreighterId()));
        theSalesOrderRequest.setFreighter(freighter);

        if (Objects.nonNull(theSalesOrderRequest.getVasActivity())) {
            VasActivity vasActivity = vasActivityRepository.findById(theSalesOrderRequest.getVasActivityId()).orElseThrow(
                            () -> new ResourceNotFoundException("VasActivity", "id", theSalesOrderRequest.getVasActivityId()));
            theSalesOrderRequest.setVasActivity(vasActivity);
        }

        List<SalesOrderSku> salesOrderSkuList = theSalesOrderRequest.getSalesOrderSku();

        for (SalesOrderSku salesOrderSku : salesOrderSkuList) {
            Sku sku = skuRepository.findById(salesOrderSku.getSkuId())
                                .orElseThrow(() -> new ResourceNotFoundException("Sku", "id",
                                salesOrderSku.getSkuId()));
            salesOrderSku.setSku(sku);

            if (Objects.nonNull(salesOrderSku.getVasActivity())) {
                VasActivity vasActivity = vasActivityRepository.findById(salesOrderSku.getVasActivityId()).orElseThrow(
                                () -> new ResourceNotFoundException("VasActivity", "id", salesOrderSku.getVasActivityId()));
                salesOrderSku.setVasActivity(vasActivity);
            }

            if (Objects.nonNull(salesOrderSku.getOrderingUomId())) {
                Uom orderingUom = uomRepository.findById(salesOrderSku.getOrderingUomId()).orElseThrow(
                                () -> new ResourceNotFoundException("OrderingUom", "id", salesOrderSku.getOrderingUomId()));
                salesOrderSku.setOrderingUom(orderingUom);    
            }

            if (Objects.nonNull(salesOrderSku.getBillingUomId())) {
                Uom billingUom = uomRepository.findById(salesOrderSku.getBillingUomId()).orElseThrow(
                                () -> new ResourceNotFoundException("BillingUom", "id", salesOrderSku.getBillingUomId()));
                salesOrderSku.setBillingUom(billingUom);    
            }
        }

        return null;
    }

    @Override
    public List<SalesOrder> saveSalesOrders(List<SalesOrder> theSalesOrder) {
        List<SalesOrder> salesOrderList = salesOrderRepository.saveAll(theSalesOrder);
        
        for (SalesOrder salesOrder : salesOrderList) {
            if (Objects.isNull(salesOrder.getSoNo()) || salesOrder.getSoNo().isEmpty()){
                String strId = String.valueOf(salesOrder.getId());
                while (strId.length() != 6) {
                    strId = "0"+strId;
                }
                int year = Integer.parseInt(new SimpleDateFormat("yy").format(new Date()));

                String soNo =  salesOrder.getPrimaryCompany().getDocNoPrefix()+"_SO_"+year+"_"+strId;
                salesOrder.setSoNo(soNo);
                salesOrder = salesOrderRepository.save(salesOrder);
            }
        }

        return salesOrderList;
    }

    @Override
    public void deleteAllSalesOrderSku(List<SalesOrderSku> theSalesOrderSkuList) {
        salesOrderSkuRepository.deleteAll(theSalesOrderSkuList);
    }

    @Override
    public Page<SalesOrder> findSalesOrderWithFilters(PageHelper helper, SalesOrderRequest request) {
        return salesOrderSearchRepository.findAllWithFilters(helper,request);
    }

    @Override
    public List<SalesOrder> findSalesOrderWithFilters(SalesOrderRequest request) {
        return salesOrderSearchRepository.findAllWithFilters(request);
    }

    @Override
    public String validateVehicleInwardRequest(VehicleInward theVehicleInwardRequest) {
        Freighter freighter = freighterRepository.findById(theVehicleInwardRequest.getFreighterId()).orElseThrow(
             () -> new ResourceNotFoundException("Freighter", "id", theVehicleInwardRequest.getFreighterId()));
        theVehicleInwardRequest.setFreighter(freighter);

        if (Objects.nonNull(theVehicleInwardRequest.getStateId())) {
            State state = stateRepository.findById(theVehicleInwardRequest.getStateId()).orElseThrow(
                            () -> new ResourceNotFoundException("VasActivity", "id", theVehicleInwardRequest.getStateId()));
            theVehicleInwardRequest.setState(state);
        }

        return null;
    }

    @Override
    public List<VehicleInward> saveVehicleInward(List<VehicleInward> theVehicleInwardList) {
        return vehicleInwardRepository.saveAll(theVehicleInwardList);
    }

    @Override
    public Page<SalesOrderCustomResponse> findSalesOrderWithFcNo(PageHelper helper, SalesOrderRequest request) {
        return vehicleInwardSearchRepository.findAllWithFilters(helper, request);
    }

    @Override
    public List<SalesOrderCustomResponse> findSalesOrderWithFcNoAll(SalesOrderRequest request) {
        return vehicleInwardSearchRepository.findAllWithFilters(request);
    }

    @Override
    public void approveSalesOrders(List<SalesOrderRequest> theSalesOrderRequestList) {
        salesOrderSearchRepository.approveSalesOrder(theSalesOrderRequestList);
    }

    @Override
    public Page<SalesOrder> findPendingSalesOrder(PageHelper helper, SalesOrderRequest request) {
       return pendingSalesOrderSearchRepository.findAllWithFilters(helper, request);
    }

    @Override
    public List<SalesOrder> findAllPendingSalesOrder(SalesOrderRequest request) {
        return pendingSalesOrderSearchRepository.findAllWithFilters(request);
    }

    @Override
    public void saveSalesOrderSkuReason(List<SalesOrderSkuRequest> theSalesOrderSkuRequestList) {
        pendingSalesOrderSearchRepository.updateReasonSalesOrderSku(theSalesOrderSkuRequestList);
    }
    
}
