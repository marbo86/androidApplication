﻿/* This file contains the User class.
* This class stores all data about a specific user.
*
* Datei: User.cs Autor: Ramandeep Singh
* Datum: 23.12.2015 Version: 1.0
*/

using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace Webserver.Models
{
    [Table("Users")]
    public class User
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public virtual int Id { get; set; }

        public virtual string Username { get; set; }
        public virtual string PhoneNumber { get; set; }
        public virtual float? Latitude { get; set; }
        public virtual float? Longitude { get; set; }

        [Column(TypeName = "DateTime2")]
        [JsonProperty(ItemConverterType = typeof (IsoDateTimeConverter))]
        public virtual DateTime? LocationUpdateTime { get; set; }
    }
}