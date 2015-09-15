package ee.hm.dop.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import ee.hm.dop.dao.PortfolioDAO;
import ee.hm.dop.model.Portfolio;

@RunWith(EasyMockRunner.class)
public class PortfolioServiceTest {

    @TestSubject
    private PortfolioService portfolioService = new PortfolioService();

    @Mock
    private PortfolioDAO portfolioDAO;

    @Test
    public void get() {
        int portfolioId = 125;
        Portfolio portfolio = createMock(Portfolio.class);
        expect(portfolioDAO.findById(portfolioId)).andReturn(portfolio);

        replayAll(portfolio);

        Portfolio result = portfolioService.get(portfolioId);

        verifyAll(portfolio);

        assertSame(portfolio, result);
    }

    private void replayAll(Object... mocks) {
        replay(portfolioDAO);

        if (mocks != null) {
            for (Object object : mocks) {
                replay(object);
            }
        }
    }

    private void verifyAll(Object... mocks) {
        verify(portfolioDAO);

        if (mocks != null) {
            for (Object object : mocks) {
                verify(object);
            }
        }
    }

}