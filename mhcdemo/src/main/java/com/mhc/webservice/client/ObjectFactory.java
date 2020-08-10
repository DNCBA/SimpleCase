//
//package com.mhc.webservice.client;
//
//import javax.xml.bind.JAXBElement;
//import javax.xml.bind.annotation.XmlElementDecl;
//import javax.xml.bind.annotation.XmlRegistry;
//import javax.xml.namespace.QName;
//
//
///**
// * This object contains factory methods for each
// * Java content interface and Java element interface
// * generated in the com.mhc.webservice.client package.
// * <p>An ObjectFactory allows you to programatically
// * construct new instances of the Java representation
// * for XML content. The Java representation of XML
// * content can consist of schema derived interfaces
// * and classes representing the binding of schema
// * type definitions, element declarations and model
// * groups.  Factory methods for each of these are
// * provided in this class.
// *
// */
//@XmlRegistry
//public class ObjectFactory {
//
//    private final static QName _FindResponse_QNAME = new QName("http://impl.service.webservice.mhc.com/", "findResponse");
//    private final static QName _Find_QNAME = new QName("http://impl.service.webservice.mhc.com/", "find");
//
//    /**
//     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mhc.webservice.client
//     *
//     */
//    public ObjectFactory() {
//    }
//
//    /**
//     * Create an instance of {@link Find }
//     *
//     */
//    public Find createFind() {
//        return new Find();
//    }
//
//    /**
//     * Create an instance of {@link FindResponse }
//     *
//     */
//    public FindResponse createFindResponse() {
//        return new FindResponse();
//    }
//
//    /**
//     * Create an instance of {@link User }
//     *
//     */
//    public User createUser() {
//        return new User();
//    }
//
//    /**
//     * Create an instance of {@link JAXBElement }{@code <}{@link FindResponse }{@code >}}
//     *
//     */
//    @XmlElementDecl(namespace = "http://impl.service.webservice.mhc.com/", name = "findResponse")
//    public JAXBElement<FindResponse> createFindResponse(FindResponse value) {
//        return new JAXBElement<FindResponse>(_FindResponse_QNAME, FindResponse.class, null, value);
//    }
//
//    /**
//     * Create an instance of {@link JAXBElement }{@code <}{@link Find }{@code >}}
//     *
//     */
//    @XmlElementDecl(namespace = "http://impl.service.webservice.mhc.com/", name = "find")
//    public JAXBElement<Find> createFind(Find value) {
//        return new JAXBElement<Find>(_Find_QNAME, Find.class, null, value);
//    }
//
//}
