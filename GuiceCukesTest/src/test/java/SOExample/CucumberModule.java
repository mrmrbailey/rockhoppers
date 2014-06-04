package SOExample;

import com.google.inject.AbstractModule;

public class CucumberModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().bind(AService.class).to(AServiceImpl.class);
        binder().bind(AnotherService.class).to(AnotherServiceImpl.class);
    }
}
