package se.yrgo.services.calls;

import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.services.customers.CustomerManagementMockImpl;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerManagementServiceProductionImpl;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;
import se.yrgo.services.diary.DiaryManagementServiceMockImpl;

import java.util.Collection;

public class CallHandlingServiceImpl implements CallHandlingService {
    private CustomerManagementService customerService;
    private DiaryManagementService diaryService;

    public CallHandlingServiceImpl(CustomerManagementMockImpl customerManagementMock, DiaryManagementServiceMockImpl diaryManagementServiceMock) {
        this.customerService = customerManagementMock;
        this.diaryService = diaryManagementServiceMock;
    }

    public CallHandlingServiceImpl(CustomerManagementServiceProductionImpl customerManagementServiceProduction, DiaryManagementServiceMockImpl diaryManagementServiceMock) {
        this.customerService = customerManagementServiceProduction;
        this.diaryService = diaryManagementServiceMock;
    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {

        customerService.recordCall(customerId, newCall);

        if (actions != null) {
            for (Action action : actions) {
                diaryService.recordAction(action);
            }
        }

    }


}
