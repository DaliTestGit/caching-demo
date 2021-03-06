package nl.jsprengers.caching;

import com.google.common.collect.ImmutableMap;
import nl.jsprengers.caching.db.PostcodeDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostcodeServiceTest {

    @InjectMocks
    PostcodeService service;

    @Mock
    PostcodeDao postcodeDao;

    @Test
    public void getPostCodeQueriesDao() {
        service.getPostcode("5941ED");
        verify(postcodeDao).findByCode("5941ED");
    }

}