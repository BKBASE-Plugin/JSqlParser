select owner
     ,      object_type
     ,      cast(
               collect(DISTINCT object_name)
                  as varchar2_ntt) as object_names
     from   all_objects
     group  by
            owner
     ,      object_type