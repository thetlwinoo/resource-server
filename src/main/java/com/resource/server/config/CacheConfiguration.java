package com.resource.server.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.resource.server.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.resource.server.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.resource.server.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.AddressTypes.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Culture.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Addresses.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.BusinessEntityAddress.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ShipMethod.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PersonEmailAddress.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PersonPhone.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PhoneNumberType.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ContactType.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.BusinessEntityContact.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Countries.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.StateProvinces.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Cities.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.SystemParameters.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.TransactionTypes.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.People.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PaymentMethods.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.DeliveryMethods.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.SupplierCategories.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Suppliers.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.SupplierTransactions.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.CurrencyRate.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PurchaseOrders.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PurchaseOrders.class.getName() + ".purchaseOrderLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PurchaseOrderLines.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.BuyingGroups.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.CustomerCategories.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Customers.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.CustomerTransactions.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PaymentTransactions.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.InvoiceLines.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Invoices.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Invoices.class.getName() + ".invoiceLineLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.OrderLines.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Orders.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Orders.class.getName() + ".orderLineLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Reviews.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Reviews.class.getName() + ".reviewLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ReviewLines.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.SpecialDeals.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.SpecialDeals.class.getName() + ".cartDiscounts", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.SpecialDeals.class.getName() + ".orderDiscounts", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ColdRoomTemperatures.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PackageTypes.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.StockGroups.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.StockItemHoldings.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Products.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductDescription.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductDocument.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductTransactions.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Locations.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductInventory.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductModel.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductModel.class.getName() + ".descriptions", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductSubCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductPhoto.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.UnitMeasure.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.StockItemStockGroups.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.StockItemTransactions.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.VehicleTemperatures.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ShoppingCarts.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ShoppingCarts.class.getName() + ".cartItemLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ShoppingCartItems.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Wishlists.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Wishlists.class.getName() + ".wishlistLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.WishlistProducts.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Compares.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Compares.class.getName() + ".compareLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.CompareProducts.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.EntityAuditEvent.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductReview.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductTags.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Reviews.class.getName() + ".reviewLineLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Merchants.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.PurchaseOrders.class.getName() + ".purchaseOrderLineLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.StockItems.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.WarrantyTypes.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductAttribute.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductAttributeSet.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductOption.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductOptionSet.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductChoice.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductSet.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductSetDetails.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductModelDescription.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductBrand.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.ProductCatalog.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Photos.class.getName(), jcacheConfiguration);
            cm.createCache(com.resource.server.domain.StockItems.class.getName() + ".photoLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.Products.class.getName() + ".stockItemLists", jcacheConfiguration);
            cm.createCache(com.resource.server.domain.StockItems.class.getName() + ".specialDiscounts", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
