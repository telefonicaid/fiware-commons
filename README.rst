Commons library - Overview
____________________________


| |Build Status| |Coverage Status|


What you get
============


Fiware commons library used in paasmanager, sdc and puppetwrapper


How to use DAO with this library
=========================

  As you can see, the provided interface is a template using generic data types:

  * The first template parameter is the <<class>> of the entity to be managed by the DAO.

  * The second template parameter is the <<type>> of the entity's business key:

  The code below is a common example of how to use BaseDAO.::

   public interface ExampleDao extends BaseDAO<Example, String> {
        // Add here some additional methods only if needed
        List<Example> findByCriteria(ExampleSearchCriteria criteria);
   }

If you want to use the JPA-Based implementation you only have to write the code bellow::

    public class ExampleDaoJpaImpl extends AbstractBaseJpaDao<Example, String> implements ExampleDao {
    /**
     * @see AbstractBaseDao#findAll()
     */
        public List<Example> findAll() throws DaoException {
            return findAll(Example.class);
        }

    /**
     * @see AbstractBaseDao#load(java.io.Serializable)
     */
    public Example load(String id) throws NoSuchEntityException {
        return loadByUniqueField(
                Example.class, FIELD_USERNAME, id);
    }

    // The rest of methods are already implements
    //...

    /**
    * This method shall be implemented
    */
    List<Example> findByCriteria(ExampleSearchCriteria criteria) {
        ...
    }
}


Installation
==============
  If you are using maven in your project, you simply add this dependency to your pom.xml::

        <dependency>
            <groupId>com.telefonica.fiware</groupId>
            <artifactId>commons</artifactId>
            <version>${commons-version}</version>
        </dependency>




.. IMAGES

.. |Build Status| image::  https://travis-ci.org/telefonicaid/fiware-commons.svg
   :target: https://travis-ci.org/telefonicaid/fiware-commons
.. |Coverage Status| image:: https://coveralls.io/repos/telefonicaid/fiware-commons/badge.svg?branch=develop
   :target: https://coveralls.io/r/telefonicaid/fiware-commons


